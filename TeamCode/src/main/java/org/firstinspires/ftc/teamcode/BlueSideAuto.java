package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by Ryan Branstetter on 10/4/17.
 */

@Autonomous(name = "BlueSideAuto", group = "autonomous")
public class BlueSideAuto extends CustomLinearOpMode {
    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "BLUE";
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


        moveSquares(-.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        template = 'R';


        if (template == 'L') {
            //strafe left
            strafeBlueAssisted(.4, 55, 90);

        } else if (template == 'C') {
            // align with center column
            strafeBlueAssisted(.4, 40, 90);

        } else if (template == 'R') {
            //strafe right
            strafeBlueAssisted(.4, 25, 90);
        } stopMotors();
        Thread.sleep(500);

        moveSquares(.15, .20);
        stopMotors();
    }
}
