package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.content.Context;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import for_camera_opmodes.OpModeCamera;

import org.opencv.android.BaseLoaderCallback;
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

    //public CustomOpMode customopmode = new CustomOpMode();

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this.hardwareMap.appContext) { //FIND OUT WHAT THE CONTEXT FOR THIS CLASS IS!!!
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    telemetry.addData("OPENCV", "OpenCV Manager Connected");
                    telemetry.update();
                    //from now onwards, you can use OpenCV API
                    break;
                case LoaderCallbackInterface.INIT_FAILED:
                    telemetry.addData("OPENCV", "Init Failed");
                    telemetry.update();
                    break;
                case LoaderCallbackInterface.INSTALL_CANCELED:
                    telemetry.addData("OPENCV", "Install Cancelled");
                    telemetry.update();
                    break;
                case LoaderCallbackInterface.INCOMPATIBLE_MANAGER_VERSION:
                    telemetry.addData("OPENCV", "Incompatible Version");
                    telemetry.update();
                    break;
                case LoaderCallbackInterface.MARKET_ERROR:
                    telemetry.addData("OPENCV", "Market Error");
                    telemetry.update();
                    break;
                default:
                    telemetry.addData("OPENCV", "OpenCV Manager Install");
                    telemetry.update();
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    //opencv Object Declarations
    public Mat imgMat; //Mat = matrix
    public Mat CMat;
    public Imgproc myProc; //Imgproc = Image Processing Object (used to identify circles/modify image)

    private double dp = 1.0; //variables used to locate circles in image
    private double minDst = 5.0;

    public void init() {

        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, this.hardwareMap.appContext, mLoaderCallback)) { //FIND OUT WHAT THE CONTEXT FOR THIS CLASS IS!!!
            telemetry.addData("Cannot connect to OpenCV Manager", "Failure");
            telemetry.update();
        }

        myProc = new Imgproc(); //initializations

        startCamera(); //camera init
        telemetry.addData("Camera", "Initialized");
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