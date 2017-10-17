package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by Ryan Branstetter on 10/4/17.
 */

@Autonomous(name = "RedSideAuto", group = "autonomous")
public class RedSideAuto extends CustomLinearOpMode {
    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "RED";
        waitForStart();



    /*
    // testing jewels, psuedo code
    // if always detects on left jewel

    // rightWallServo.setPosition(.25);

    if (getColor().equals(AutoColor)) {
        startMotors(-.10, -.10);
        Thread.sleep(100); }
    else if (!getColor.equals(AutoColor) && !getColor.equals("GREEN")){
        startMotors(.10, .10);
        Thread.sleep(100); }
    else {
        wallServo.setPosition(0); }

    // wallServo.setPosition(0);
    */

        moveSquares(.70, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);
        stopMotors();
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        template = 'R';

        boolean side = true;
        if (template == 'L') {
            //strafe left
            strafeRedAssisted(.4, 45, 90);

        } else if (template == 'C') {
            // align with center column
            strafeRedAssisted(.4, 30, 90);

        } else if (template == 'R') {
            //strafe right
            strafeRedAssisted(.4, 15, 90);
        } stopMotors();

        moveSquares(.15, .20);
        stopMotors();
    }
}
