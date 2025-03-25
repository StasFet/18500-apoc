package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.pedropathing.localization.GoBildaPinpointDriver;

import pedroPathing.constants.FConstants;

public class MyRobot extends Robot {

    //declare core stuff
    public IMU imu;
    IMU.Parameters imu_params;
    public GoBildaPinpointDriver odo;
    Telemetry telemetry;
    public Follower follower;
    OpModeType opmode;

    //declare gamepads
    public GamepadEx gp_general;
    public GamepadEx gp_drive;

    //declare drivetrain + intake motors
    public DcMotorEx fr, fl, br, bl;
    public DcMotorEx intake_spin;

    //declare slide motors
    public DcMotorEx vslide_1, vslide_2;
    public DcMotorEx intake_slide;

    //declare hang servos
    public Servo hang_servo_left;
    public Servo hang_servo_right;

    public enum OpModeType {
        TELE_OP,
        AUTO
    }

    //pinpoint odometry set-up
    private void initPinpointOdometry(HardwareMap hMap) {
        odo = hMap.get(GoBildaPinpointDriver.class, "pinpoint");
        odo.resetPosAndIMU();
        odo.setOffsets(0, 0);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

    }
    private void initIMU(HardwareMap hMap) {
        //imu stuff - no longer needed because of pinpoint odo
        imu = hMap.get(IMU.class, "imu");
        imu_params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imu_params);
    }
    private void initGamepads(Gamepad gp1, Gamepad gp2) {
        //initialise gamepads
        gp_general = new GamepadEx(gp1);
        gp_drive = new GamepadEx(gp2);
    }
    private void initDriveMotors(HardwareMap hMap) {
        //initialise motors
        fr = hMap.get(DcMotorEx.class, "rightFront");
        fl = hMap.get(DcMotorEx.class, "leftFront");
        br = hMap.get(DcMotorEx.class, "rightRear");
        bl = hMap.get(DcMotorEx.class, "leftRear");
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void initOtherMotors(HardwareMap hMap) {
        vslide_1 = hMap.get(DcMotorEx.class, "vslide_1");
        vslide_2 = hMap.get(DcMotorEx.class, "vslide_2");
        intake_slide = hMap.get(DcMotorEx.class, "intake_slide");
        intake_spin = hMap.get(DcMotorEx.class, "intake");

        //reset encoders on relevant motors that will use ticks for velocity PIDs
        vslide_1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide_2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake_slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void initServos(HardwareMap hMap) {
        hang_servo_left = hMap.get(Servo.class, "hang_left");
        hang_servo_right = hMap.get(Servo.class, "hang_right");
    }

    public MyRobot(HardwareMap hMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry t, OpModeType type) {
        opmode = type;
        follower = new Follower(hMap);

        telemetry = t;

        if (opmode == OpModeType.TELE_OP) {
            //initPinpointOdometry(hMap);
            initIMU(hMap);
            initGamepads(gamepad1, gamepad2);
            initDriveMotors(hMap);
            //initOtherMotors(hMap);
            //initServos(hMap);
        }

    }
}
