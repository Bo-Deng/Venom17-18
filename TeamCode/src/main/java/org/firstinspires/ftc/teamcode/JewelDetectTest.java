package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;
import android.content.Context;
import android.os.Environment;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import for_camera_opmodes.OpModeCamera;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.InstallCallbackInterface;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Bo on 9/30/2017.
 */

@Autonomous (name = "JewelDetect", group = "test")
public class JewelDetectTest extends OpModeCamera {

    public void startOpenCV(){
       BaseLoaderCallback openCVLoaderCallback = null;
       try {
           openCVLoaderCallback = new BaseLoaderCallback(hardwareMap.appContext) {
               @Override
               public void onManagerConnected(int status) {
                   switch (status) {
                       case LoaderCallbackInterface.SUCCESS: {
                           telemetry.addData("OpenCV", "OpenCV Manager connected!");
                       }
                       break;
                       default: {
                           super.onManagerConnected(status);
                       }
                       break;
                   }
               }
           };
       } catch (NullPointerException e) {
           telemetry.addData("Could not find OpenCV Manager!", "Please install the app from the Google Play Store.");
       }

       if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, this.hardwareMap.appContext, openCVLoaderCallback)) { //FIND OUT WHAT THE CONTEXT FOR THIS CLASS IS!!!
           telemetry.addData("Cannot connect to OpenCV Manager", "Failure");
       }
   }

    public Mat imgMat; //Mat = matrix
    public Mat CMat;

    private double dp = 1.2d; //ratio of input resolution  to output resolution
    private double minDst = 100; //min distance between centers of detected circles (replace with jewel radius)

    int loopCount = 0;

    public void init() {

        startOpenCV(); //load opencvlibrary

        startCamera(); //camera init
        telemetry.addData("Camera", "Initialized");
        telemetry.update();
    }

    public void loop() {

        if (imageReady()) { //when image received from camera

            Bitmap img; //creates a bitmap of the image

            img = convertYuvImageToRgb(yuvImage, width, height, 1);

            //imgMat = new Mat();
            imgMat = new Mat(new Size(img.getWidth(), img.getHeight()), CvType.CV_8UC1); //put image into a matrix

            Utils.bitmapToMat(img, imgMat);

            Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2GRAY, 0); //convert to grayscale

            CMat = new Mat(imgMat.size(), CvType.CV_8UC1); //creates new matrix to store circle data

            /* edge detection & image blur if necessary
            myProc.blur(imgMat, imgMat, new Size(width, height)); //blurs image to eliminate false circles
            myProc.Canny(edgeMat, edgeMat, 80, 100);*/

            Imgproc.HoughCircles(imgMat, CMat, Imgproc.CV_HOUGH_GRADIENT, dp, minDst); //find circles in image (add parameters based on jewel dimensions to increase accuracy)

            // telemetry.addData("Num of Circles:", CMat.cols()); //return number of circles (# of columns = # of circles)
            // printCircleData(CMat); //method to print x, y coordinates and radius of the circles detected
            telemetry.update();

            /*try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                telemetry.addData("Exception", e);
                telemetry.update();
            }

            if (loopCount < 10)
                writeToFile(imgMat, CMat);  // use this method to print circles in CMat onto the image in imgMat before saving to device

            if (loopCount == 9)
                telemetry.update();

            loopCount++;*/
        }
    }

    public void writeToFile(Mat mat, Mat circle){ //debugging only; prints images into data files on phone, access through camera

        //code to add circle to img goes here

        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bmp);
        } catch (CvException e) {
            telemetry.addData("Exception", "creating bitmap:" + e);
        }

        mat.release();

        FileOutputStream out = null;

        String filename = "frame" + loopCount + ".png";

        File sd = new File(Environment.getExternalStorageDirectory() + "/frames");
        boolean success = true;
        if (!sd.exists()) {
            success = sd.mkdir();
        }
        if (success) {
            File dest = new File(sd, filename);

            try {
                out = new FileOutputStream(dest);
                bmp.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance

            } catch (Exception e) {
                e.printStackTrace();
                telemetry.addData("Exception", "creating bitmap:" + e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                        telemetry.addData("File Saved", "Loop Count: " + loopCount);
                    }
                } catch (IOException e) {
                    telemetry.addData("Error: ", e);
                    e.printStackTrace();
                }
            }
        }
    }

    //public void printCircleData(Mat circle) {}
}