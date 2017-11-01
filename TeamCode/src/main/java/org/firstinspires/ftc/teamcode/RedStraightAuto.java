package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.HashMap;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.vuforia.ar.pl.DebugLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by Ryan Bransteter on 10/3/17.
 */

@Autonomous(name = "RedStraightAuto", group = "autonomous")
public class RedStraightAuto extends CustomLinearOpMode {

    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "RED";
        waitForStart();
        getJewelColor();
        getVuMark();


        knockBall(AutoColor);
        Thread.sleep(200);

        grabBlock();
        Thread.sleep(200);

        moveSquares(.75, .20);
        stopMotors();
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getRightDistance());


        if (template == 'L') {
            //strafe left
                strafeRedAssisted(.4, 75, 0);

        } else if (template == 'C') {
            // align with center column
                strafeRedAssisted(.4, 60, 0);

        } else if (template == 'R') {
            //strafe right
            strafeRedAssisted(.4, 45, 0);
        } stopMotors();
        Thread.sleep(500);

        moveSquares(.15, .20);
        stopMotors();


    }
}
