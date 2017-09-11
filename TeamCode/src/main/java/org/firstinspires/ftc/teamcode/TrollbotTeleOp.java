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

        if (Math.abs(gamepad1.left_stick_y) > .1) {

            startLMotor(gamepad1.left_stick_y);
        }
        else {
            stopMotor();
        }

        if (Math.abs(gamepad1.right_stick_y) > .1) {

            startRMotor(gamepad1.right_stick_y);
        }
        else {
            stopMotor();
        }

    }

    public void startLMotor(double lSpeed) {
        motorFL.setPower(lSpeed);
        motorBL.setPower(lSpeed);
    }

    public void startRMotor(double rSpeed) {
        motorFL.setPower(rSpeed);
        motorBL.setPower(rSpeed);
    }

    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
