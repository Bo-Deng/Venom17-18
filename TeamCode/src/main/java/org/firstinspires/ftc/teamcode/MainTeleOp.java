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



    public void init() {

        initStuff(hardwareMap);


        telemetry.addData("init ", "completed");
        telemetry.update();
    }
    @Override
    public void loop() {

        // for testing hardware mapping
        if (gamepad1.a)
            motorBL.setPower(1);
        if (gamepad1.b)
            motorBR.setPower(1);
        if (gamepad1.y)
            motorFL.setPower(1);
        if (gamepad1.x)
            motorFR.setPower(1);

        if (gamepad1.dpad_up) {
            motorBL.setPower(.2);
            motorBR.setPower(.2);
            motorFL.setPower(.2);
            motorFR.setPower(.2);
        }

        /*// the signs might need to be switched
        if (Math.abs(gamepad2.right_stick_y) > 0.1) {
            motorXLift.setPower(gamepad2.right_stick_y);
        }
        if (Math.abs(gamepad2.left_stick_y) > 0.1) {
            motorYLift.setPower(gamepad2.right_stick_y);
        } */

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
            servoLHug.setPosition(Range.clip(servoLHug.getPosition() - .025, 0, 1)); //0
        }
        else if (gamepad2.dpad_right) {
            servoLHug.setPosition(Range.clip(servoLHug.getPosition() + .025, 0, 1)); //.225
        }

        if (gamepad2.x) {
            servoRHug.setPosition(Range.clip(servoRHug.getPosition() - .025, 0, 1)); //.775
        }
        if (gamepad2.b) {
            servoRHug.setPosition(Range.clip(servoRHug.getPosition() + .025, 0, 1)); //1
        }

        telemetry.addData("MotorFLEncoder", motorFL.getCurrentPosition());
        telemetry.addData("MotorFREncoder", motorFR.getCurrentPosition());
        telemetry.addData("MotorBLEncoder", motorBL.getCurrentPosition());
        telemetry.addData("MotorBREncoder", motorBR.getCurrentPosition());
        telemetry.addData("rangeL cm: ", getLeftDistance());
        telemetry.addData("rangeR cm: ", getRightDistance());
        telemetry.addData("servoLHug Position: ", servoLHug.getPosition());
        telemetry.addData("servoRHug Position: ", servoRHug.getPosition());
    }

    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
