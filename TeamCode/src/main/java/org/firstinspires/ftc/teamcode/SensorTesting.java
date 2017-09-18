package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by Bo on 9/18/2017.
 */
@TeleOp(name="SensorTesting", group="opMode")
public class SensorTesting extends OpMode {
    IMU imu;
    ModernRoboticsI2cRangeSensor range;


    public void init() {
        imu = new IMU(hardwareMap.get(BNO055IMU.class, "IMU"));
        imu.IMUinit(hardwareMap);
        range = hardwareMap.get(ModernRoboticsI2cRangeSensor.class, "range");

        telemetry.addData("Yaw: ", imu.getYaw());
        telemetry.addData("Range: ", range.getDistance(DistanceUnit.INCH));
    }

    public void loop() {
        telemetry.update();
    }
}
