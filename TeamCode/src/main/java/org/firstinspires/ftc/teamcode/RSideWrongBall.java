package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/25/17.
 */

@Autonomous(name = "RSideWrongBall", group = "autonomous")
public class RSideWrongBall extends CustomLinearOpMode {
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

        moveSquares(.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);
        stopMotors();
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        boolean side = true;
        if (template == 'L') {
            //strafe left
            strafeRedAssisted(.4, 50, 90);

        } else if (template == 'C') {
            // align with center column
            strafeRedAssisted(.4, 35, 90);

        } else if (template == 'R') {
            //strafe right
            strafeRedAssisted(.4, 20, 90);
        } stopMotors();

        Thread.sleep(500);

        servoLHug.setPosition(.4);
        servoRHug.setPosition(.6);

        moveSquares(.15, .20);
        stopMotors();
    }
}
