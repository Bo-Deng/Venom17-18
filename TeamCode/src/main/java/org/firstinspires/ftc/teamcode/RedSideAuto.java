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

        moveSquares(.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);

        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        template = 'R';

        boolean side = true;
        if (template == 'L') {
            //strafe left
            if (getRightDistance() > 65) {
                side = false; }
            strafeAssisted(side, .4, 65, 90, AutoColor);
        } else if (template == 'C') {
            // align with center column
            if (getRightDistance() > 40) {
                side = false; }
            strafeAssisted(side, .4, 40, 90, AutoColor);
        } else if (template == 'R') {
            //strafe right
            if (getRightDistance() > 25) {
                side = false; }
            strafeAssisted(side, .4, 25, 90, AutoColor);
        } stopMotors();

        moveSquares(.15, .20);
        stopMotors();
    }
}
