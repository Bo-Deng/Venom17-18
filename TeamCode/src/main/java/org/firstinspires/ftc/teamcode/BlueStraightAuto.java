package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/4/17.
 */

@Autonomous(name = "BlueStraightAuto", group = "autonomous")
public class BlueStraightAuto extends CustomLinearOpMode {
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

        moveSquares(.8, -.20);

        Pturn(180);
    //Turn towards glyph container.


    //Align with correct column.

        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        if (template == 'L') {
        //strafe left
        while (getRightDistance() < 80) {
            strafe(0, .2); }
    } else if (template == 'C') {
        // align with center column
        while (getRightDistance() < 60) {
            strafe(0, .2); }
    } else if (template == 'R') {
        //strafe right
        while (getRightDistance() < 40) {
            strafe(0, .2); }
    } stopMotors();

    moveSquares(.3, .20);
    }
}
