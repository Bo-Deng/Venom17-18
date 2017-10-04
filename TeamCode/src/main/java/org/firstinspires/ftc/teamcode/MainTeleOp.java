package org.firstinspires.ftc.teamcode;

/**
 * Created by Ryan Bransteter on 9/30/17.
 */

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name="Main teleop", group="opMode")
public class MainTeleOp extends OpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    //DcMotor motorYLift;
    //DcMotor motorXLift;
    IMU imu;

    double maintainAngle;

    public void init() {

        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");
        //motorYLift = hardwareMap.dcMotor.get("motorYLift");
        //motorXLift = hardwareMap.dcMotor.get("motorXLift");
        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

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
            motorBL.setPower(yL);
            motorFL.setPower(yL);
            motorBR.setPower(yR);
            motorFR.setPower(yR);
        }
        // strafe right
        else if (rt != 0){
            motorBL.setPower(rt);
            motorFL.setPower(-rt);
            motorBR.setPower(-rt);
            motorFR.setPower(rt);
        }
        // strafe left
        else if (lt != 0){
            motorBL.setPower(-lt);
            motorFL.setPower(lt);
            motorBR.setPower(lt);
            motorFR.setPower(-lt);
        }
        else
            stopMotor();

        telemetry.addData("MotorFLEncoder", motorFL.getCurrentPosition());
        telemetry.addData("MotorFREncoder", motorFR.getCurrentPosition());
        telemetry.addData("MotorBLEncoder", motorBL.getCurrentPosition());
        telemetry.addData("MotorBREncoder", motorBR.getCurrentPosition());
    }

    public void stopMotor() {
        motorFL.setPower(0);
        motorFR.setPower(0);
        motorBL.setPower(0);
        motorBR.setPower(0);
    }
}
