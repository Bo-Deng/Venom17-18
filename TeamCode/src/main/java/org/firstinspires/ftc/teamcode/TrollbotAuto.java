package org.firstinspires.ftc.teamcode;

import android.hardware.Sensor;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;


/**
 * Created by Bo on 9/4/2017.
 */
public class TrollbotAuto extends LinearOpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    ModernRoboticsI2cRangeSensor rangeSensor;


    public void runOpMode() throws InterruptedException {

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        rangeSensor = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "sensor_range");

        waitForStart();

        /*

        //This is where the phone would detect the jewel and then the servo would hit the right one.

        //Then it would scan the Vumark and 'decode' it. A 'column' variable would tell the robot where to place its glyph.

        int column = 0;
        if (Vumark == ???)
            column = 1;
        else if (Vumark == ???)
            column = 2;
        else if (Vumark == ???)
            column = 3;

        //Forward till close to glyph container. (using range sensor??)

        startMotors(1, 1);
        while (rangeSensor.getDistance(DistanceUnit.CM) > ???) {//do nothing}

        stopMotors();

        //Turn towards glyph container.

        turn(1, ????);

        //Align with correct column.

        if (column == 1) {
            //strafe left
            strafe(0, 1, ?);
        }
        else if (column == 2) {
            // align with center column
            strafe(?, 1, ?);
        }
        else if (column == 3) {
            //strafe right
            strafe(1, 1, ?);
        }

        // Place glyph into column and park.

        startMotors(1, 1);
        Thread.sleep(????);
        stopMotors();

        */
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

    public void strafe(int d, int p, int t) throws InterruptedException { // d = direction, p = power, t = time
        if (d == 0) { //left
            motorFL.setPower(-p);
            motorFR.setPower(p);
            motorBL.setPower(p);
            motorBR.setPower(-p);
        }
        else if (d == 1) { //right
            motorFL.setPower(p);
            motorFR.setPower(-p);
            motorBL.setPower(-p);
            motorBR.setPower(p);
        }
        Thread.sleep(t);
        stopMotors();
    }
}