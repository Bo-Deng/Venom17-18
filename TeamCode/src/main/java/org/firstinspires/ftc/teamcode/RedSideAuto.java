package org.firstinspires.ftc.teamcode;

import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/4/17.
 */

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

        startMotors(.20, .20);
        Thread.sleep(1 * 1000);

        stopMotors();

        turn(.2, 500);

        //Turn towards glyph container.


        //Align with correct column.

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        if (template == 'L') {
            //strafe left
            while (getLeftDistance() < 100) {
                strafe(0, 1);
            }
            stopMotors();
        } else if (template == 'C') {
            // align with center column

            while (getLeftDistance() < 80) {
                strafe(0, 1);
            }
            stopMotors();
        } else if (template == 'R') {
            //strafe right
            while (getLeftDistance() < 60) {
                strafe(0, 1);
            }
            stopMotors();
        }
        else {
            strafe(0, 1);
            Thread.sleep(100 * 1);
        }
        startMotors(.20, .20);
        Thread.sleep(1000 * 1);
    }
}
