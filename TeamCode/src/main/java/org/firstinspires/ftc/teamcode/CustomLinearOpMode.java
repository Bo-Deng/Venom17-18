package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Bo on 9/13/2017.
 */
public class CustomLinearOpMode extends LinearOpMode {
    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    @Override
    public void runOpMode() throws InterruptedException {

    }

    public void initStuff(HardwareMap map) throws InterruptedException {
        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");
    }

    public void setMode(DcMotor.RunMode runMode) throws InterruptedException {
        motorFR.setMode(runMode);
        idle();
        motorFL.setMode(runMode);
        idle();
        motorBR.setMode(runMode);
        idle();
        motorBL.setMode(runMode);
        idle();
    }
}
