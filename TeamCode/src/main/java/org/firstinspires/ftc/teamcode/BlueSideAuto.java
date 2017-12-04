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

        getJewelColor();
        getVuMark();

        knockBall(AutoColor);
        Thread.sleep(200);

        grabBlock();
        Thread.sleep(200);

        moveSquares(-.75, .20);
        stopMotors();

        liftDown();
        Thread.sleep(500);

        Pturn(90);
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        if (template == 'L') {
            //strafe left
            strafeBlueAssisted(.4, 25, 90);
            DebugLog.LOGE("VuMark: ", "L");

        } else if (template == 'C') {
            // align with center column
            strafeBlueAssisted(.4, 40, 90);
            DebugLog.LOGE("VuMark: ", "C");

        } else if (template == 'R') {
            //strafe right
            strafeBlueAssisted(.4, 55, 90);
            DebugLog.LOGE("VuMark: ", "R");
        } stopMotors();

        DebugLog.LOGE("End Distance: ", "" + getLeftDistance());

        Thread.sleep(500);

        servoLHug.setPosition(.4);
        servoRHug.setPosition(.6);

        moveTime(500, .4);
        stopMotors();
    }
}
