package org.firstinspires.ftc.teamcode;

import com.vuforia.ar.pl.DebugLog;

/**
 * Created by hannahbransteter on 10/25/17.
 */

public class BSideWrongBall extends CustomLinearOpMode {
    public void runOpMode() throws InterruptedException {
        initStuff(hardwareMap);

        AutoColor = "BLUE";
        waitForStart();

        knockWrongBall(AutoColor);

        moveSquares(-.75, .20);
        stopMotors();
        Thread.sleep(500);

        Pturn(90);
        Thread.sleep(500);

        DebugLog.LOGE("startDistance ", "" + getLeftDistance());

        template = 'R';


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

        moveSquares(.15, .20);
        stopMotors();
    }
}
