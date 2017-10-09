package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/4/17.
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

        moveSquares(1, .20);

        Pturn(90);

        //Turn towards glyph container.


        //Align with correct column.

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        if (template == 'L') {
            //strafe left
            while (getLeftDistance() < 170) {
                strafe(0, .2); }
        } else if (template == 'C') {
            // align with center column
            while (getLeftDistance() < 150) {
                strafe(0, .2); }
        } else if (template == 'R') {
            //strafe right
            while (getLeftDistance() < 130) {
                strafe(0, .2); }
        } stopMotors();

        moveSquares(1, .20);
        stopMotors();
    }
}
