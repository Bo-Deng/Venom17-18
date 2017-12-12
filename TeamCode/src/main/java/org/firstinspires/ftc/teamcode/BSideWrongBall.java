package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/25/17.
 */

@Autonomous(name = "BSideWrongBall", group = "autonomous")
public class BSideWrongBall extends CustomLinearOpMode {
    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "BLUE";
        waitForStart();

        getJewelColor();
        getVuMark();

        knockWrongBall(AutoColor);
        Thread.sleep(200);

        grabBlock();
        Thread.sleep(200);

        moveSquares(-.85, .20);
        stopMotors();

        Thread.sleep(500);

        Pturn(90);
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        if (template == 'L') {
            //strafe left
            strafeBlueAssisted(.5, 25, 90);

        } else if (template == 'C') {
            // align with center column
            strafeBlueAssisted(.5, 40, 90);

        } else if (template == 'R') {
            //strafe right
            strafeBlueAssisted(.5, 59, 90);
        } stopMotors();
        Thread.sleep(500);

        servoLHug.setPosition(.4);
        servoRHug.setPosition(.6);

        wiggleNoRight(.4, 90);
        stopMotors();
        sleep(250);
        backUp();
    }
}
