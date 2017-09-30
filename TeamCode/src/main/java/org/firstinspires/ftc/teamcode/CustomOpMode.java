package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by Bo on 9/25/2017.
 */
public class CustomOpMode extends OpMode {

    DcMotor motorFR;
    DcMotor motorFL;
    DcMotor motorBR;
    DcMotor motorBL;

    public void init() {

    }

    public void loop() {

    }

    public void initStuff(HardwareMap map) {
        motorFR = map.dcMotor.get("motorFR");
        motorFL = map.dcMotor.get("motorFL");
        motorBR = map.dcMotor.get("motorBR");
        motorBL = map.dcMotor.get("motorBL");
    }

    public void setMode(DcMotor.RunMode runMode){
        motorFR.setMode(runMode);
        motorFL.setMode(runMode);
        motorBR.setMode(runMode);
        motorBL.setMode(runMode);
    }
}