package org.firstinspires.ftc.teamcode;

import android.graphics.Bitmap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsUsbServoController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.io.IOException;

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
    ModernRoboticsI2cRangeSensor rangeSensorL;
    ModernRoboticsI2cRangeSensor rangeSensorR;

    Servo servoLHug;
    Servo servoRHug;

    Servo servoUpDownArm;
    Servo servoLeftRightArm;;


    DcMotor motorXLift;
    DcMotor motorYLift;

    String AutoColor;
    char template;
    boolean jewelIsRed;

    int squaresToEncoder = 1120; //use motorBL

    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    ElapsedTime time;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void initStuff(HardwareMap map) throws InterruptedException {
        time = new ElapsedTime();

        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        rangeSensorL = map.get(ModernRoboticsI2cRangeSensor.class, "rangeL");
        rangeSensorR = map.get(ModernRoboticsI2cRangeSensor.class, "rangeR");

        servoLHug = map.servo.get("servoLHug");
        servoRHug = map.servo.get("servoRHug");
        servoLeftRightArm = map.servo.get("servoLeftRightArm");
        servoUpDownArm = map.servo.get("servoUpDownArm");

        motorXLift = map.dcMotor.get("motorXLift");
        motorYLift = map.dcMotor.get("motorYLift");

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        servoLHug.setPosition(1);
        servoRHug.setPosition(0);



        sleep(3000);

        telemetry.addLine("startJewelCamera initialization started");
        telemetry.update();

        setCameraDownsampling(2);

        telemetry.addLine("Wait for camera to finish initializing!");

        startCamera();  // can take a while.
        // best started before waitForStart
        sleep(2000);
        telemetry.addLine("Camera ready!");

        time.reset();
        while (time.seconds() < 5 && opModeIsActive()) {
            if (imageReady()) { // only do this if an image has been returned from the camera
                int redValue = 0;
                int blueValue = 0;
                int greenValue = 0;

                // get image, rotated so (0,0) is in the bottom left of the preview window
                Bitmap rgbImage;
                rgbImage = convertYuvImageToRgb(yuvImage, width, height, 1);

                for (int x = (int) (.8 * rgbImage.getWidth()); x < rgbImage.getWidth(); x++) {
                    for (int y = (int) (.75 * rgbImage.getHeight()); y < rgbImage.getHeight(); y++) {
                        int pixel = rgbImage.getPixel(x, y);
                        redValue += red(pixel);
                        blueValue += blue(pixel);
                        greenValue += green(pixel);
                    }
                }

                jewelIsRed = redValue > blueValue;
                telemetry.addData("Is Jewel Red? ", jewelIsRed);

            }
            telemetry.update();
            sleep(10);
        }
        stopCamera();

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
        time.reset();

        while (vuMark == RelicRecoveryVuMark.UNKNOWN && time.seconds() < 3) {
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

        servoUpDownArm.setPosition(.5);
        servoLeftRightArm.setPosition(.3);


        telemetry.addData("PID value = ", ".0275");
        telemetry.addData("init = ", "completed");

        telemetry.update();
    }

    public void initStuff(HardwareMap map, boolean auto) throws InterruptedException {
        time = new ElapsedTime();

        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        rangeSensorL = map.get(ModernRoboticsI2cRangeSensor.class, "rangeL");
        rangeSensorR = map.get(ModernRoboticsI2cRangeSensor.class, "rangeR");

        servoLHug = map.servo.get("servoLHug");
        servoRHug = map.servo.get("servoRHug");
        servoLeftRightArm = map.servo.get("servoLeftRightArm");
        servoUpDownArm = map.servo.get("servoUpDownArm");

        motorXLift = map.dcMotor.get("motorXLift");
        motorYLift = map.dcMotor.get("motorYLift");

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        if (auto) {
            servoLHug.setPosition(1);
            servoRHug.setPosition(0);

            servoUpDownArm.setPosition(.69);
            servoLeftRightArm.setPosition(1);

            telemetry.addLine("startJewelCamera initialization started");
            telemetry.update();

            setCameraDownsampling(8);

            telemetry.addLine("Wait for camera to finish initializing!");
            telemetry.update();
            startCamera();  // can take a while.
            // best started before waitForStart
            telemetry.addLine("Camera ready!");

            time.reset();
            while (time.seconds() < 3 && opModeIsActive()) {
                if (imageReady()) { // only do this if an image has been returned from the camera
                    int redValue = 0;
                    int blueValue = 0;
                    int greenValue = 0;

                    // get image, rotated so (0,0) is in the bottom left of the preview window
                    Bitmap rgbImage;
                    rgbImage = convertYuvImageToRgb(yuvImage, width, height, 1);

                    for (int x = (int) (.8 * rgbImage.getWidth()); x < rgbImage.getWidth(); x++) {
                        for (int y = (int) (.75 * rgbImage.getHeight()); y < rgbImage.getHeight(); y++) {
                            int pixel = rgbImage.getPixel(x, y);
                            redValue += red(pixel);
                            blueValue += blue(pixel);
                            greenValue += green(pixel);
                        }
                    }

                    jewelIsRed = redValue > blueValue;
                    telemetry.addData("Is Jewel Red? ", jewelIsRed);

                }
                telemetry.update();
                sleep(10);
            }
            stopCamera();

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
            time.reset();

            while (vuMark == RelicRecoveryVuMark.UNKNOWN && time.seconds() < 3) {
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
        }

        telemetry.addData("PID value = ", ".0275");
        telemetry.addData("init = ", "completed");

        telemetry.update();
    }

    public void stopMotors() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void startMotors(double speed) {
        motorFL.setPower(speed);
        motorFR.setPower(speed);
        motorBL.setPower(speed);
        motorBR.setPower(speed);
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

    public void strafe(int d, double p) throws InterruptedException { // d = direction, p = power

        if (d == 0) { //left
            motorFL.setPower(-p);
            motorFR.setPower(p);
            motorBL.setPower(p);
            motorBR.setPower(-p);
        } else if (d == 1) { //right
            motorFL.setPower(p);
            motorFR.setPower(-p);
            motorBL.setPower(-p);
            motorBR.setPower(p);
        }
    }

    public double getRightDistance() {
        double dist = rangeSensorR.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist) && opModeIsActive()) {
            dist = rangeSensorR.getDistance(DistanceUnit.CM);
        }
        return dist;
    }

    public double getLeftDistance() {
        double dist = rangeSensorL.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist) && opModeIsActive()) {
            dist = rangeSensorL.getDistance(DistanceUnit.CM);
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
    public void moveSquares(double squares, double power) throws InterruptedException{
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        double encoderStart = Math.abs(motorFR.getCurrentPosition());
        if (squares > 0) {
            while (Math.abs(motorFR.getCurrentPosition()) < encoderStart +(squares * squaresToEncoder) && opModeIsActive()) {
                startMotors(power);

            }
        }
        else {
            while (-Math.abs(motorFR.getCurrentPosition()) > encoderStart + (squares * squaresToEncoder) && opModeIsActive()) {
                startMotors(-power);
            }
        }
        stopMotors();
    }
    public void setMotors(double FLpow, double BLpow, double FRpow, double BRpow) {
        motorFL.setPower(Range.clip(FLpow, -1, 1));
        motorBL.setPower(Range.clip(BLpow, -1, 1));
        motorFR.setPower(Range.clip(FRpow, -1, 1));
        motorBR.setPower(Range.clip(BRpow, -1, 1));
    }

    @Deprecated
    public void strafeAssisted(boolean isLeft, double power, double stopRangeCM) { //pass true to strafe left, false to strafe right
        power = Math.abs(power);
        double desiredAngle = imu.getYaw();
        if (isLeft) {
            while (getRightDistance() < stopRangeCM) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.02; //.025 < PID <.03
                // While this range does work on the trollbot, it has not been tested on the actual robot.
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(-power - PIDchange, power - PIDchange, power + PIDchange, -power + PIDchange);
            }
        }
        else {
            while (getLeftDistance() < stopRangeCM) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.02; //.025 < PID <.03
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(power - PIDchange, -power - PIDchange, -power + PIDchange, power + PIDchange);
            }
        }
    }

    public void strafeRedAssisted(double power, double stopRangeCM, double angle) { //pass true to strafe left, false to strafe right
        power = Math.abs(power);
        double desiredAngle = angle;


        /*if (isLeft) {
            while (getRightDistance() < stopRangeCM && opModeIsActive()) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.02; //.025 < PID <.03
                // While this range does work on the trollbot, it has not been tested on the actual robot.
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(-power - PIDchange, power - PIDchange, power + PIDchange, -power + PIDchange);
            }
        }
        else {
            while (getRightDistance() > stopRangeCM && opModeIsActive()) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.02; //.025 < PID <.03
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(power - PIDchange, -power - PIDchange, -power + PIDchange, power + PIDchange);
            }
        } */

        if(getRightDistance() > stopRangeCM) {
            while (getRightDistance() > stopRangeCM && opModeIsActive()) {
                strafeRight(power, angle);
            }
        }
        if (getRightDistance() < stopRangeCM) {
            while (getRightDistance() < stopRangeCM && opModeIsActive()) {
                strafeLeft(power, angle);
            }
        }
    }

    public void strafeBlueAssisted(double power, double stopRangeCM, double angle) { //pass true to strafe left, false to strafe right
        power = Math.abs(power);
        double desiredAngle = angle;

        if(getLeftDistance() < stopRangeCM) {
            while (getLeftDistance() < stopRangeCM && opModeIsActive()) {
                strafeRight(power, angle);
            }
        }
        if (getLeftDistance() > stopRangeCM) {
            while (getLeftDistance() > stopRangeCM && opModeIsActive()) {
                strafeLeft(power, angle);
            }
        }
    }

    public void strafeRight(double power, double angle) {
        power = Math.abs(power);
        double desiredAngle = angle;

        double diffFromDesired = imu.getTrueDiff(desiredAngle);
        double kP = 0.01; //.025 < PID <.03
        double PIDchange;

        PIDchange = kP * diffFromDesired;

        setMotors(power - PIDchange, -power - PIDchange, -power + PIDchange, power + PIDchange);
    }

    public void strafeLeft(double power, double angle) {
        power = Math.abs(power);
        double desiredAngle = angle;

        double diffFromDesired = imu.getTrueDiff(desiredAngle);
        double kP = 0.01; //.025 < PID <.03
        double PIDchange;

        PIDchange = kP * diffFromDesired;

        setMotors(-power - PIDchange, power - PIDchange, power + PIDchange, -power + PIDchange);
    }


    public void straightAssisted(double squares) throws InterruptedException {
        straightAssisted(squares, imu.getYaw());
    }

    public void straightAssisted(double squares, double angle) throws InterruptedException {
        setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setTargetPosition((int) -squares * squaresToEncoder);
        motorFL.setTargetPosition((int) squares * squaresToEncoder);
        motorBR.setTargetPosition((int) -squares * squaresToEncoder);
        motorBL.setTargetPosition((int) squares * squaresToEncoder);
    }

    public void Pturn(double angle) throws InterruptedException {
        double kP = .5/90;
        double PIDchange;
        double angleDiff = imu.getTrueDiff(angle);
        time.reset();
        while (Math.abs(angleDiff) > 0.5 && opModeIsActive() && time.seconds() < 2) {
            angleDiff = imu.getTrueDiff(angle);
            PIDchange = angleDiff * kP;

            if (PIDchange < 0) {
                motorFR.setPower(Range.clip(PIDchange - .1, -1, 1));
                motorBR.setPower(Range.clip(PIDchange - .1, -1, 1));
                motorFL.setPower(Range.clip(-PIDchange + .1, -1, 1));
                motorBL.setPower(Range.clip(-PIDchange + .1, -1, 1));
            }
            else {
                motorFR.setPower(Range.clip(PIDchange + .1, -1, 1));
                motorBR.setPower(Range.clip(PIDchange + .1, -1, 1));
                motorFL.setPower(Range.clip(-PIDchange - .1, -1, 1));
                motorBL.setPower(Range.clip(-PIDchange - .1, -1, 1));
            }
        }
        stopMotors();
    }
    public double getDist(String color) {
        if (color.equals("RED")) {
            return getRightDistance();
        }
        return getLeftDistance();
    }

    public void knockBall(String color) {
        servoLeftRightArm.setPosition(.3);
        servoUpDownArm.setPosition(0);


        if (jewelIsRed && color.equals("RED")) {
           servoLeftRightArm.setPosition(0);
       } else if (!jewelIsRed && color.equals("RED")) {
           servoLeftRightArm.setPosition(.45);
       }
        if (jewelIsRed && color.equals("BLUE")) {
            servoLeftRightArm.setPosition(.45);
        } else if (!jewelIsRed && color.equals("BLUE")) {
            servoLeftRightArm.setPosition(0);
        }

       servoUpDownArm.setPosition(.55);
    }
    public void knockWrongBall(String color) {
        servoLeftRightArm.setPosition(.3);
        servoUpDownArm.setPosition(0);

        if (jewelIsRed && color.equals("RED")) {
            servoLeftRightArm.setPosition(.45);
        } else if (!jewelIsRed && color.equals("RED")) {
            servoLeftRightArm.setPosition(0);
        }
        if (jewelIsRed && color.equals("BLUE")) {
            servoLeftRightArm.setPosition(0);
        } else if (!jewelIsRed && color.equals("BLUE")) {
            servoLeftRightArm.setPosition(.45);
        }

        servoUpDownArm.setPosition(.55);
    }
}
