package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;

public class MyRobot extends Robot {
    //declare core stuff
    public IMU imu;
    IMU.Parameters imu_params;
    public GoBildaPinpointDriver odo;
    Telemetry telemetry;

    //declare gamepads
    public GamepadEx gp_general;
    public GamepadEx gp_drive;

    //declare drivetrain motors
    public DcMotorEx fr, fl, br, bl;

    //declare slide motors
    public DcMotorEx vslide_1, vslide_2;
    public DcMotorEx intake_slide;

    //constants subclass

    public MyRobot(HardwareMap hMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry t) {

        //imu stuff - no longer needed because of pinpoint odo
        imu = hMap.get(IMU.class, "imu");
        imu_params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imu_params);

        //pinpoint odometry set-up
        //@TODO: measure offset, directions
        odo = hMap.get(GoBildaPinpointDriver.class, "odo");
        odo.resetPosAndIMU();
        odo.setOffsets(0, 0);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

        telemetry = t;

        //initialise gamepads
        gp_general = new GamepadEx(gamepad1);
        gp_drive = new GamepadEx(gamepad2);

        //initialise motors
        fr = hMap.get(DcMotorEx.class, "fr");
        fl = hMap.get(DcMotorEx.class, "fl");
        br = hMap.get(DcMotorEx.class, "br");
        bl = hMap.get(DcMotorEx.class, "bl");
        vslide_1 = hMap.get(DcMotorEx.class, "vslide_1");
        vslide_2 = hMap.get(DcMotorEx.class, "vslide_2");
        intake_slide = hMap.get(DcMotorEx.class, "intake_slide");

        //reset encoders for all motors
        vslide_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake_slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
