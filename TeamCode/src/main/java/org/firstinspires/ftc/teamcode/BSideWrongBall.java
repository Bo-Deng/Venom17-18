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

        moveSquares(-.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

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

        servoLHug.setPosition(.4);
        servoRHug.setPosition(.6);

        moveSquares(.15, .20);
        stopMotors();
    }
}
