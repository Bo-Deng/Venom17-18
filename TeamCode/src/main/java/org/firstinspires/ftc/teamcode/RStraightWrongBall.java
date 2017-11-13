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


        knockWrongBall(AutoColor);

        grabBlock();
        Thread.sleep(200);

        moveSquares(.75, .20);
        stopMotors();
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getRightDistance());

        template = 'R';

        if (template == 'L') {
            //strafe left
            strafeRedAssisted(.4, 75, 0);

        } else if (template == 'C') {
            // align with center column
            strafeRedAssisted(.4, 60, 0);

        } else if (template == 'R') {
            //strafe right
            strafeRedAssisted(.4, 45, 0);
        } stopMotors();
        Thread.sleep(500);

        moveSquares(.15, .20);
        stopMotors();
    }
}
