package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import for_camera_opmodes.OpModeCamera;

import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Core;


/**
 * Created by Bo on 9/30/2017.
 */

@Autonomous (name = "JewelDetect", group = "test")
public class JewelDetectTest extends OpModeCamera {

    //Object Declarations
    public Mat imgMat; //Mat = matrix
    public Mat CMat;
    public Imgproc myProc; //Imgproc = Image Processing Object (used to identify circles/modify image)

    private double dp = 1.0; //variables used to locate circles in image
    private double minDst = 5.0;

    public OpenCVLoader openCVLoader; //loads the opencv library

    public void init() {

        telemetry.addData("loading", "libs");
        telemetry.update();
        System.loadLibrary("opencv_java"); //loads opencv libs
        telemetry.addData("loading", "complete");
        telemetry.update();

        setCameraDownsampling(2); //down sampling = lower resolution for higher speed; TEST THIS (may be unnecessary)

        myProc = new Imgproc(); //initializations
        openCVLoader = new OpenCVLoader();

        startCamera(); //camera init
        telemetry.addData("Camera", "Initialized");
        telemetry.update();

        if (openCVLoader.initDebug()) { //inits opencv libs
            telemetry.addData("OpenCV Library", "Found");
        }
        else {
            telemetry.addData("OpenCV Library", "Not Found");
        }
        telemetry.update();

    }

    public void loop() {

        if (imageReady()) { //when image received from camera

            Bitmap img; //creates a bitmap of the image

            img = convertYuvImageToRgb(yuvImage, width, height, 1);

            //program crashes here
            //imgMat = new Mat();
            imgMat = new Mat(new Size(img.getWidth(), img.getHeight()), CvType.CV_8UC1); //supposed to put image in matrix

            Utils.bitmapToMat(img, imgMat);

            myProc.blur(imgMat, imgMat, new Size(width, height)); //blurs image to eliminate false circles

            CMat = new Mat(imgMat.size(), CvType.CV_8UC1); //creates new matrix to store circle data

            /* edge detection if necessary
            myProc.cvtColor(imgMat, edgeMat, Imgproc.COLOR_RGB2GRAY, 0);

            myProc.Canny(edgeMat, edgeMat, 80, 100);*/

            myProc.HoughCircles(imgMat, CMat, Imgproc.CV_HOUGH_GRADIENT, dp, minDst); //find circles

            telemetry.addData("Num of Circles:", CMat.cols()); //columns determine num of circles
            telemetry.update();
        }
    }
}