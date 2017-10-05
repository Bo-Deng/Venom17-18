package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import for_camera_opmodes.LinearOpModeCamera;

/**
 * Created by Bo on 9/13/2017.
 */
public class CustomLinearOpMode extends LinearOpModeCamera {
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    IMU imu;
    ModernRoboticsI2cRangeSensor leftRangeSensor;
    ModernRoboticsI2cRangeSensor rightRangeSensor;
    // Servo rightWallServo;

    String AutoColor;
    char template;

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void initStuff(HardwareMap map) throws InterruptedException {
        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        telemetry.addData("init ", "completed");
        telemetry.update();

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXb/g5n/////AAAAGSUed2rh5Us1jESA1cUn5r5KDUqTfwO2woh7MxjiLKSUyDslqBAgwCi0Qmc6lVczErnF5TIw7vG5R4TJ2igvrDVp+dP+3i2o7UUCRRj/PtyVgb4ZfNrDzHE80/6TUHifpKu4QCM04eRWYZocWNWhuRfytVeWy6NSTWefM9xadqG8FFrFk3XnvqDvk/6ZAgerNBdq5SsJ90eDdoAhgYEee40WxasoUUM9YVMvkWOqZgHSuraV2IyIUjkW/u0O+EkFtTNRUWP+aZwn1qO1H4Lk07AJYe21eqioBLMdzY7A8YqR1TeQ//0WJg8SFdXjuGbF6uHykBe2FF5UeyaehA0iTqfPS+59FLm8y1TuUt57eImq";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate"); // can help in debugging; otherwise not necessary

        telemetry.addData(">", "Press Play to start");

        relicTrackables.activate();


        // copy pasta from the ftc ppl
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);


        telemetry.addData("VuMark ", vuMark);
        while (vuMark == RelicRecoveryVuMark.UNKNOWN) {
            vuMark = RelicRecoveryVuMark.from(relicTemplate);
        }

        template = ' ';

        telemetry.addData("VuMark ", vuMark);
        if (vuMark == RelicRecoveryVuMark.CENTER)
            template = 'C';
        else if (vuMark == RelicRecoveryVuMark.LEFT)
            template = 'L';
        else if (vuMark == RelicRecoveryVuMark.RIGHT)
            template = 'R';

        setCameraDownsampling(8);

        telemetry.addLine("Wait for camera to finish initializing!");
        telemetry.update();
        startCamera();  // can take a while.
        // best started before waitForStart
        telemetry.addLine("Camera ready!");

        telemetry.update();
    }

    public void stopMotors() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void startMotors(double rSpeed, double lSpeed) {
        motorFL.setPower(lSpeed);
        motorFR.setPower(rSpeed);
        motorBL.setPower(lSpeed);
        motorBR.setPower(rSpeed);
    }

    public void turn(double speed, int time) throws InterruptedException {
        startMotors(speed, -speed);
        Thread.sleep(time);
        stopMotors();
    }

    public void setMode(DcMotor.RunMode runMode) throws InterruptedException {
        motorFR.setMode(runMode);
        idle();
        motorFL.setMode(runMode);
        idle();
        motorBR.setMode(runMode);
        idle();
        motorBL.setMode(runMode);
        idle();
    }

    public void strafe(int d, int p) throws InterruptedException { // d = direction, p = power

        if (d == 0) { //left
            motorFL.setPower(p);
            motorFR.setPower(-p);
            motorBL.setPower(-p);
            motorBR.setPower(p);
        } else if (d == 1) { //right
            motorFL.setPower(-p);
            motorFR.setPower(p);
            motorBL.setPower(p);
            motorBR.setPower(-p);
        }
    }

    public double getRightDistance() {
        double dist = rightRangeSensor.getDistance(DistanceUnit.CM);
        while (dist > 1000) {
            dist = rightRangeSensor.getDistance(DistanceUnit.CM);
        }
        return dist;
    }

    public double getLeftDistance() {
        double dist = leftRangeSensor.getDistance(DistanceUnit.CM);
        while (dist > 1000) {
            dist = leftRangeSensor.getDistance(DistanceUnit.CM);
        }
        return dist;
    }

    public String getColor() {
        String colorString = "NONE";

        // linear OpMode, so could do stuff like this too.
        /*
        motorLeft = hardwareMap.dcMotor.get("motor_1");
        motorRight = hardwareMap.dcMotor.get("motor_2");
        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        */

        if (isCameraAvailable()) {

            setCameraDownsampling(8);
            // parameter determines how downsampled you want your images
            // 8, 4, 2, or 1.
            // higher number is more downsampled, so less resolution but faster
            // 1 is original resolution, which is detailed but slow
            // must be called before super.init sets up the camera

            telemetry.addLine("Wait for camera to finish initializing!");
            telemetry.update();
            startCamera();  // can take a while.
            // best started before waitForStart
            telemetry.addLine("Camera ready!");
            telemetry.update();



            // LinearOpMode, so could do stuff like this too.
            /*
            motorLeft.setPower(1);  // drive forward
            motorRight.setPower(1);
            sleep(1000);            // for a second.
            motorLeft.setPower(0);  // stop drive motors.
            motorRight.setPower(0);
            sleep(1000);            // wait a second.
            */

            int ds2 = 2;

            while (opModeIsActive()) {
                if (imageReady()) { // only do this if an image has been returned from the camera
                    int redValue = 0;
                    int blueValue = 0;
                    int greenValue = 0;

                    // get image, rotated so (0,0) is in the bottom left of the preview window
                    Bitmap rgbImage;
                    rgbImage = convertYuvImageToRgb(yuvImage, width, height, ds2);

                    for (int x = 0; x < rgbImage.getWidth(); x++) {
                        for (int y = 0; y < rgbImage.getHeight(); y++) {
                            int pixel = rgbImage.getPixel(x, y);
                            redValue += red(pixel);
                            blueValue += blue(pixel);
                            greenValue += green(pixel);
                        }
                    }
                    int color = highestColor(redValue, greenValue, blueValue);

                    switch (color) {
                        case 0:
                            colorString = "RED";
                            break;
                        case 1:
                            colorString = "GREEN";
                            break;
                        case 2:
                            colorString = "BLUE";
                    }

                } else {
                    colorString = "NONE";
                }

                telemetry.addData("Color:", "Color detected is: " + colorString);
                telemetry.update();
                sleep(10);
            }
            stopCamera();


        }
        return colorString;
    }
}
