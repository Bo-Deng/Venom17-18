package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Bo on 9/20/2017.
 */
public class PIDtesting extends OpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    IMU imu;

    final static int squaresToEncoder = 1084; //NEED TO TEST & GET CORRECT VALUE

    public void init() {
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        motorFR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        telemetry.addData("init ", "completed");
        telemetry.update();
    }
    public void loop() {
        if (gamepad1.left_trigger > .1) {
            strafeAssisted(true);
        }
        else if (gamepad1.right_trigger > .1) {
            strafeAssisted(false);
        }
        else {
            setMotors(0, 0, 0, 0);
        }

    }

    public void setMotors(double FLpow, double BLpow, double FRpow, double BRpow) {
        motorFL.setPower(Range.clip(FLpow, -1, 1));
        motorBL.setPower(Range.clip(BLpow, -1, 1));
        motorFR.setPower(Range.clip(FRpow, -1, 1));
        motorBR.setPower(Range.clip(BRpow, -1, 1));
    }

    public void strafeAssisted(boolean lTrig) { //pass true to strafe left, false to strafe right
        double desiredAngle = imu.getYaw();
        if (lTrig) {
            while (gamepad1.left_trigger > .1) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.005;
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(-.5 - PIDchange, .5 - PIDchange, .5 + PIDchange, -.5 + PIDchange);
            }
        }
        else {
            while (gamepad2.right_trigger > .1) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.005;
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(.5 - PIDchange, -.5 - PIDchange, -.5 + PIDchange, .5 + PIDchange);
            }
        }
    }

    public void straightAssisted(double squares) {
        straightAssisted(squares, imu.getYaw());
    }

    public void straightAssisted(double squares, double angle) {

    }

}