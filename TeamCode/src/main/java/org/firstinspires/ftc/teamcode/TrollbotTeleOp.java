package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Bo on 9/4/2017.
 */
public class TrollbotTeleOp extends OpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;

    public void init() {

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public void loop() {

        // if statement for forward/backward
        if (Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.left_stick_x) < .1) {

            startMotor(gamepad1.left_stick_y);
        }
        // if statement for left/right
        if (Math.abs(gamepad1.left_stick_y) < .1 && Math.abs(gamepad1.left_stick_x) > .1)
        {
            motorFR.setPower(gamepad1.left_stick_x);
            motorBR.setPower(-gamepad1.left_stick_x);
            motorFL.setPower(-gamepad1.left_stick_x);
            motorBL.setPower(gamepad1.left_stick_x);
        }
        if (Math.abs(gamepad1.left_stick_y) > .1 && Math.abs(gamepad1.left_stick_x) > .1)
        {
            double x = gamepad1.left_stick_x;
            double y = gamepad1.left_stick_y;
            double r = Math.pow(Math.pow(x,2) + Math.pow(y ,2), 1/2);
            double max = Math.abs(Math.max(Math.abs(r), Math.max(Math.abs(x), Math.abs(y))));
            motorFL.setPower((x + y + r) / max);
            motorFR.setPower((x - y - r) / max);
            motorBL.setPower((x - y + r) / max);
            motorBR.setPower((x + y - r) / max);
        }
        else
            stopMotor();


    }

    public void startMotor(double Speed) {
        motorFL.setPower(-Speed);
        motorBL.setPower(-Speed);
        motorFR.setPower(Speed);
        motorBR.setPower(Speed);
    }



    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
