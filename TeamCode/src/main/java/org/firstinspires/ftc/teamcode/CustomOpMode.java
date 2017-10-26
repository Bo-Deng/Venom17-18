package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by Bo on 9/25/2017.
 */
public class CustomOpMode extends OpMode {

    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    Servo servoLHug;
    Servo servoRHug;

    Servo servoUpDownArm;
    Servo servoLeftRightArm;

    IMU imu;

    int squaresToEncoder = 1120; //use motorBL

    ElapsedTime time;

    ModernRoboticsI2cRangeSensor rangeSensorL;
    ModernRoboticsI2cRangeSensor rangeSensorR;

    DcMotor motorXLift;
    DcMotor motorYLift;

    String AutoColor;


    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    public void init() {

    }

    public void loop() {

    }

    public void initStuff(HardwareMap map) {
        time = new ElapsedTime();

        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");

        servoLHug = map.servo.get("servoLHug");
        servoRHug = map.servo.get("servoRHug");
        servoLeftRightArm = map.servo.get("servoLeftRightArm");
        servoUpDownArm = map.servo.get("servoUpDownArm");

        motorXLift = map.dcMotor.get("XLift");
        motorYLift = map.dcMotor.get("YLift");

        servoLHug.setPosition(0);
        servoRHug.setPosition(1);

        servoUpDownArm.setPosition(1);



        rangeSensorL = map.get(ModernRoboticsI2cRangeSensor.class, "rangeL");
        rangeSensorR = map.get(ModernRoboticsI2cRangeSensor.class, "rangeR");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }

    public void setMode(DcMotor.RunMode runMode){
        motorFR.setMode(runMode);
        motorFL.setMode(runMode);
        motorBR.setMode(runMode);
        motorBL.setMode(runMode);
    }

    public double getRightDistance() {
        double dist = rangeSensorR.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist)) {
            dist = rangeSensorR.getDistance(DistanceUnit.CM);
        }
        return dist;
    }

    public double getLeftDistance() {
        double dist = rangeSensorL.getDistance(DistanceUnit.CM);
        while (dist > 1000 || Double.isNaN(dist)) {
            dist = rangeSensorL.getDistance(DistanceUnit.CM);
        }
        return dist;
    }
}
