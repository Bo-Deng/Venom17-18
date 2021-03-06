package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/25/17.
 */

@Autonomous(name = "RStraightWrongBall", group = "autonomous")
public class RStraightWrongBall extends CustomLinearOpMode {

    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "RED";
        waitForStart();

        getJewelColor();
        getVuMark();

        knockWrongBall(AutoColor);
        Thread.sleep(200);

        grabBlock();
        Thread.sleep(200);

        moveSquares(.60, .20);
        stopMotors();
        Thread.sleep(500);


        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        if (template == 'L') {
            //strafe left
            strafeRedAssistedPID(78, 0);

        } else if (template == 'C') {
            // align with center column
            strafeRedAssistedPID(61, 0);

        } else if (template == 'R') {
            //strafe right
            strafeRedAssistedPID(42, 0);
        } stopMotors();

        liftDown();
        Thread.sleep(500);

        servoLHug.setPosition(.4);
        servoRHug.setPosition(.6);

        wiggle(.4, 0);
        stopMotors();
        sleep(250);
        backUp();
    }
}
