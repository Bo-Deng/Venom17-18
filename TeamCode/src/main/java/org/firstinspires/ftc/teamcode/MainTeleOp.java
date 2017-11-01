package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryan Bransteter on 9/30/17.
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Main teleop", group="opMode")
public class MainTeleOp extends CustomOpMode {

    double motorScale = 1;

    public void init() {

        initStuff(hardwareMap);


        telemetry.addData("init ", "completed");
        telemetry.update();
    }
    @Override
    public void loop() {

        // for testing hardware mapping
        if (gamepad1.a) {
            motorScale = motorScale == .5 ? 1 : .5;
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }


        if (gamepad1.dpad_up) {
            motorBL.setPower(.2);
            motorBR.setPower(.2);
            motorFL.setPower(.2);
            motorFR.setPower(.2);
        }

        // the signs might need to be switched
        if (gamepad2.left_stick_y > 0.1) {
            motorXLift.setPower(gamepad2.left_stick_y / 1.5);
        }
        else if (gamepad2.left_stick_y < -0.1) {
            motorXLift.setPower(gamepad2.left_stick_y / 1.5);
        }
        else {
            motorXLift.setPower(0);
        }
        if (gamepad2.right_stick_y > 0.1) {
            motorYLift.setPower(gamepad2.right_stick_y);
        }
        else if (gamepad2.right_stick_y < -0.1) {
            motorYLift.setPower(gamepad2.right_stick_y);
        }
        else {
            motorYLift.setPower(0);
        }

        double yL = gamepad1.left_stick_y;
        double yR = gamepad1.right_stick_y;
        double rt = gamepad1.right_trigger;
        double lt = gamepad1.left_trigger;

        // forwards and backwards
        if (Math.abs(yL) > .1 || Math.abs(yR) > .1) {
            motorBL.setPower(-yL);
            motorFL.setPower(-yL);
            motorBR.setPower(-yR);
            motorFR.setPower(-yR);
        }
        // strafe right
        else if (rt != 0){
            motorBL.setPower(-rt);
            motorFL.setPower(rt);
            motorBR.setPower(rt);
            motorFR.setPower(-rt);
        }
        // strafe left
        else if (lt != 0){
            motorBL.setPower(lt);
            motorFL.setPower(-lt);
            motorBR.setPower(-lt);
            motorFR.setPower(lt);
        }
        else
            stopMotor();


        if (gamepad2.dpad_left) {
            //servoLHug.setPosition(Range.clip(servoLHug.getPosition() - .025, 0, 1)); //0
            servoLeftRightArm.setPosition(Range.clip(servoLeftRightArm.getPosition() - .025, 0, 1));
        }
        else if (gamepad2.dpad_right) {
            //servoLHug.setPosition(Range.clip(servoLHug.getPosition() + .025, 0, 1)); //.225

            servoLeftRightArm.setPosition(Range.clip(servoLeftRightArm.getPosition() + .025, 0, 1));
        }


        if (gamepad2.dpad_up) {
            servoUpDownArm.setPosition(Range.clip(servoUpDownArm.getPosition() + .025, 0, 1));
        }
        else if (gamepad2.dpad_down) {
            servoUpDownArm.setPosition(Range.clip(servoUpDownArm.getPosition() - .025, 0, 1));
        }

        if (gamepad2.x) {
            servoRHug.setPosition(Range.clip(servoRHug.getPosition() - .025, 0, 1)); //.775
        }
        if (gamepad2.b) {
            servoRHug.setPosition(Range.clip(servoRHug.getPosition() + .025, 0, 1)); //1
        }

        if (gamepad2.y) {
            servoLHug.setPosition(Range.clip(servoLHug.getPosition() - .025, 0, 1)); //.775
        }
        if(gamepad2.a) {
            servoLHug.setPosition(Range.clip(servoLHug.getPosition() + .025, 0, 1)); //.775
        }

        if (gamepad2.left_bumper) {
            servoLHug.setPosition(.25);

        }
        else if (gamepad2.left_trigger > .1) {
            servoLHug.setPosition(.70);
        }

        if (gamepad2.right_bumper) {
            servoRHug.setPosition(.8);
        }
        else if (gamepad2.right_trigger > .1) {
            servoRHug.setPosition(.4);
        }

        //telemetry.addData("MotorFLEncoder", motorFL.getCurrentPosition());
        //telemetry.addData("MotorFREncoder", motorFR.getCurrentPosition());
        //telemetry.addData("MotorBLEncoder", motorBL.getCurrentPosition());
        //telemetry.addData("MotorBREncoder", motorBR.getCurrentPosition());
        //telemetry.addData("rangeL cm: ", getLeftDistance());
        //telemetry.addData("rangeR cm: ", getRightDistance());
        telemetry.addData("motorScale: ", motorScale);
        telemetry.addData("XLift: ", motorXLift.getCurrentPosition());
        telemetry.addData("YLift: ", motorYLift.getCurrentPosition());
        telemetry.addData("servoLHug Position: ", servoLHug.getPosition());
        telemetry.addData("servoRHug Position: ", servoRHug.getPosition());
        telemetry.addData("servoLeftRight Position: ", servoLeftRightArm.getPosition());
        telemetry.addData("servoUpDown Position: ", servoUpDownArm.getPosition());
    }

    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
