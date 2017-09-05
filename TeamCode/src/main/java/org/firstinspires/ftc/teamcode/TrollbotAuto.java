package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


/**
 * Created by Bo on 9/4/2017.
 */
public class TrollbotAuto extends LinearOpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;


    public void runOpMode() throws InterruptedException {

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        waitForStart();

        moveMotor(1, 3000);

        turn(1, 1000);

        moveMotor(1, 3000);
    }

    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }

    public void startMotor(double rSpeed, double lSpeed) {
        motorFL.setPower(lSpeed);
        motorFR.setPower(rSpeed);
        motorBL.setPower(lSpeed);
        motorBR.setPower(rSpeed);
    }

    public void moveMotor(double speed, int time) throws InterruptedException {

        startMotor(speed, speed);
        Thread.sleep(time);
        stopMotor();
    }

    public void turn(double speed, int time) throws InterruptedException {
        startMotor(speed, -speed);
        Thread.sleep(time);
        stopMotor();
    }
}
