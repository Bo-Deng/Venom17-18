package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by Bo on 9/20/2017.
 */
@TeleOp (name = "PIDtesting", group = "test")
public class PIDtesting extends CustomOpMode {

    DcMotor motorFL;
    DcMotor motorFR;
    DcMotor motorBL;
    DcMotor motorBR;
    IMU imu;

    ElapsedTime time = new ElapsedTime();

    final static int squaresToEncoder = 1084; //NEED TO TEST & GET CORRECT VALUE

    public void init() {
        motorFL = hardwareMap.dcMotor.get("motorFL");
        motorFR = hardwareMap.dcMotor.get("motorFR");
        motorBL = hardwareMap.dcMotor.get("motorBL");
        motorBR = hardwareMap.dcMotor.get("motorBR");

        motorFR.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBR.setDirection(DcMotorSimple.Direction.REVERSE);

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.IMUinit(hardwareMap);

        telemetry.addData("init ", "completed");
        telemetry.addData("PID value = ", ".0275");
        telemetry.update();
    }
    public void loop() {

        if (gamepad1.a) { //testing PID Turning
            try {
                Pturn(90.0, 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (gamepad1.left_trigger > .1) { //testing left strafe
            strafeAssisted(true);
        }
        else if (gamepad1.right_trigger > .1) { //testing right strafe
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
                double kP = 0.0275; //.025 < PID <.03
                // While this range does work on the trollbot, it has not been tested on the actual robot.
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(-.5 - PIDchange, .5 - PIDchange, .5 + PIDchange, -.5 + PIDchange);
            }
        }
        else {
            while (gamepad1.right_trigger > .1) {
                double diffFromDesired = imu.getTrueDiff(desiredAngle);
                double kP = 0.0275; //.025 < PID <.03
                double PIDchange;

                PIDchange = kP * diffFromDesired;

                setMotors(.5 - PIDchange, -.5 - PIDchange, -.5 + PIDchange, .5 + PIDchange);
            }
        }
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

    public void Pturn(double angle, int ms) throws InterruptedException {
        double kP = .25/90;
        double PIDchange;
        double angleDiff;
        time.reset();
        while (time.milliseconds() < ms) {
            angleDiff = imu.getTrueDiff(angle);
            PIDchange = angleDiff * kP;
            motorFR.setPower(Math.abs(PIDchange) > .05 ? PIDchange : 0);
            motorBR.setPower(Math.abs(PIDchange) > .05 ? PIDchange : 0);
            motorFL.setPower(Math.abs(PIDchange) > .05 ? -PIDchange : 0);
            motorBL.setPower(Math.abs(PIDchange) > .05 ? -PIDchange : 0);
        }
    }

}
