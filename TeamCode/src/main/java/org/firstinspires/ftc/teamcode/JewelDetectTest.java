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
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.R.attr.radius;
import static org.opencv.imgproc.Imgproc.circle;

/**
 * Created by Bo on 9/30/2017.
 */

@Autonomous (name = "JewelDetect", group = "test")
public class JewelDetectTest extends OpModeCamera {

    public void startOpenCV(){ //loads openCV library from phone via openCVManager
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

        if (!OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_3_0, this.hardwareMap.appContext, openCVLoaderCallback)) {
            telemetry.addData("Cannot connect to OpenCV Manager", "Failure");
        }
    }

    public Mat imgMat; //Mat = matrix
    public Mat CMat; //holds image data

    private double dp = 1; //ratio of input resolution  to output resolution
    private double minDst = 100; //min distance between centers of detected circles

    int loopCount = 0; //debugging purposes

    public void init() {
        startOpenCV(); //load opencvlibrary

        startCamera(); //camera init
        telemetry.addData("Camera", "Initialized");
        telemetry.update();
    }

    public void loop() {
        if (imageReady()) { //when image received from camera

            Bitmap img; //creates a bitmap of the image

            img = convertYuvImageToRgb(yuvImage, width, height, 1); //convert image to bitmap

            imgMat = new Mat(new Size(img.getWidth(), img.getHeight()), CvType.CV_8UC1); //put image into a matrix

            Utils.bitmapToMat(img, imgMat); //bitmap to mat conversion

            Imgproc.cvtColor(imgMat, imgMat, Imgproc.COLOR_RGB2GRAY, 0); //convert mat to grayscale

            CMat = new Mat(imgMat.size(), CvType.CV_8UC1); //creates new mat to store circle data

            //Imgproc.GaussianBlur(imgMat, imgMat, new Size(3, 3), 0); //blur if necessary

            //find circles in image (optimal params for PHONE AT HOME: 50, 25, 75, 125)
            //at school, circles detected from ~1ft away (70, 35, 100, 175):: May no work on robot (adjust we able to test on robot)
            Imgproc.HoughCircles(imgMat, CMat, Imgproc.CV_HOUGH_GRADIENT, dp, minDst, 70, 35, 75, 125);

            telemetry.addData("Num of Circles", CMat.cols()); //return number of circles (# of columns = # of circles)
            getCircleData(CMat, false, 0); //method to print x, y coordinates and radius of the circles detected

            if (CMat.cols() == 2)
                for (int i = 0; i < 2; i++) {
                    telemetry.addData("Jewel " + i + "'s Color is " + JewelColor(CMat, i), "It is on the " + JewelSide(CMat, i) + " side."); //prints side and color (TEST)
            }
            if (loopCount < 10) { //saves first 10 images to phone gallery
                writeToFile(imgMat, CMat);  // use this method to print circles in CMat onto the image in imgMat before saving to device
                loopCount++;
            }
        }
        else
            telemetry.addData("Image not loaded", "Loop count: " + loopCount);
        telemetry.update();
    }

    public void writeToFile(Mat mat, Mat circles) { //debugging only; prints images into data files on phone, access through camera roll/gallery

        // Draw the circles detected
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB, 0); //convert to rgb (so the circle that gets drawn is colored)

        int numberOfCircles = (circles.rows() == 0) ? 0 : circles.cols(); //confusing copypasta (retrieves data from mat, draw circle based on data, converts mat to bitmap, saves to system directory = Gallery)
        try {
            for (int i = 0; i < numberOfCircles; i++) {
                double[] circleCoordinates = circles.get(0, i);
                int x = (int) circleCoordinates[0];
                int y = (int) circleCoordinates[1];
                Point center = new Point(x, y);
                int r = (int) circleCoordinates[2];

                // draw the circle center
                circle(mat, center, 5, new Scalar(0, 255, 0), -1);
                // draw the circle outline
                circle(mat, center, r, new Scalar(0, 0, 255), 6);
            }
        } catch (Exception e) {
        }
        Bitmap bmp = null;
        try {
            bmp = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(mat, bmp);
        } catch (CvException e) {
            telemetry.addData("Exception", "creating bitmap: " + e);
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
                    telemetry.addData("Error", e);
                    e.printStackTrace();
                }
            }
        }
    }

    public String JewelColor(Mat circle, int n) { //print what color each ball is (corresponds with JewelSide)
        Bitmap img = convertYuvImageToRgb(yuvImage, width, height, 1);
        int redValue = 0;
        int blueValue = 0;
        int pix;

        double[] list = getCircleData(circle, true, n);
        if (list != null) {
            for (int i = 0; i < list[2]; i++){
                pix = img.getPixel((int) list[0] - i, (int) list[1] - i);
                redValue += red(pix);
                blueValue += blue(pix);
            }
            for (int i = 0; i < list[2]; i++){
                pix = img.getPixel((int) list[0] + i, (int) list[1] + i);
                redValue += red(pix);
                blueValue += blue(pix);
            }
        }
        else {
            telemetry.addData("List is", "NULL");
            return "NULL";
        }
        String colorString = "";
        if (redValue > blueValue)
            colorString = "RED";
        else
            colorString = "BLUE";
        return colorString;
    }

    public String JewelSide(Mat circle, int num) { //print what side each ball its on (corresponds to JewelColor)
        Bitmap img = convertYuvImageToRgb(yuvImage, width, height, 1);
        double x1;
        double x2;
        double[] list;

        if (num == 0)
            list = getCircleData(circle, true, 0);
        else
            list = getCircleData(circle, true, 1);
        if (list != null) {
            x1 = list[0];
        }
        else {
            telemetry.addData("List is", "NULL");
            return "NULL";
        }
        if (num == 0)
            list = getCircleData(circle, true, 1);
        else
            list = getCircleData(circle, true, 0);
        if (list != null) {
            x2 = list[0];
        }
        else {
            telemetry.addData("List is", "NULL");
            return "NULL";
        }

        if (x1 < x2)
            return "RIGHT";
        return "LEFT";
    }

    public double[] getCircleData(Mat circle, Boolean ret, int circnum) { //prints x, y, and radius of each circle (throws NullPointerException if array has no data) OR returns list with specific circle info (you change function through boolean ret; true to return list, false to just print)
        double[] list;
        if (ret){
            try {
                // list = circle.get(circnum, 0);  should these be the other way around as below?
                list = circle.get(0, circnum);
                return list;
            } catch (NullPointerException e){
                telemetry.addData("No Data Found", "SORRY");
                return null;
            }
        }
        try {
            for (int i = 0; i < circle.cols(); i++) {
                list = circle.get(0, i);
                telemetry.addData("x", (int) list[0]);
                telemetry.addData("y", (int) list[1]);
                telemetry.addData("r", (int) list[2]);
            }
        } catch (NullPointerException e) {
            telemetry.addData("Array", "Empty");
        }
        return null;
    }
}