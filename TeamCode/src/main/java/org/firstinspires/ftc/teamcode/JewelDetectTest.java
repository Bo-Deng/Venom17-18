package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.util.Util;

import for_camera_opmodes.OpModeCamera;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

/**
 * Created by Bo on 9/30/2017.
 */
public class JewelDetectTest extends OpModeCamera {

    public Mat myMat;
    public Mat CMat;
    public Imgcodecs myCodec;
    public CascadeClassifier myCas;
    public Imgproc myProc;
    public Bitmap bitmap;

    private double dp = 1.0;
    private double minDst = 5.0;

    public void init() {

        setCameraDownsampling(2); //down sampling = lower resolution for higher speed; TEST THIS

        myCas = new CascadeClassifier();
        myCodec = new Imgcodecs();
        myMat = new Mat();
        myProc = new Imgproc();

        super.init();
        telemetry.addData("Camera", "Initialized");
        telemetry.update();

    }

    public void loop() {

        if (imageReady()) {

            Bitmap img;
            img = convertYuvImageToRgb(yuvImage, width, height, 1);
            Utils.bitmapToMat(img, myMat);

            myProc.blur(myMat, myMat, new Size(width, height));

            CMat = new Mat(myMat.size(), CvType.CV_8UC1);

            /* edge detection if necessary
            myProc.cvtColor(myMat, edgeMat, Imgproc.COLOR_RGB2GRAY, 0);

            myProc.Canny(edgeMat, edgeMat, 80, 100);*/

            myProc.HoughCircles(myMat, CMat, Imgproc.CV_HOUGH_GRADIENT, dp, minDst);

            telemetry.addData("Num of Circles:", CMat.cols()); //columns determine num of circles
            telemetry.update();
        }
    }

}