package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/25/17.
 */

@Autonomous(name = "BStraightWrongBall", group = "autonomous")
public class BStraightWrongBall extends CustomLinearOpMode {
    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "BLUE";
        waitForStart();


        knockWrongBall(AutoColor);

        moveSquares(-.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(180);
        stopMotors();
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        template = 'R';

        if (template == 'L') {
            //strafe left
            strafeBlueAssisted(.4, 80, 180);

        } else if (template == 'C') {
            // align with center column
            strafeBlueAssisted(.4, 65, 180);

        } else if (template == 'R') {
            //strafe right
            strafeBlueAssisted(.4, 50, 180);
        } stopMotors();
        Thread.sleep(500);

        moveSquares(.15, .20);
        stopMotors();
    }
}
