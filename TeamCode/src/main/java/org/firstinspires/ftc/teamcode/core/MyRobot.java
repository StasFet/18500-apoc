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

import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.state.intake.IntakeSlideStates;
import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;

public class MyRobot extends Robot {
    //declare core stuff
    public IMU imu;
    IMU.Parameters imu_params;
    public States state;
    public GoBildaPinpointDriver odo;

    //declare gamepads
    public GamepadEx gp_general;
    public GamepadEx gp_drive;

    //declare drivetrain motors
    public DcMotorEx fr, fl, br, bl;

    //declare slide motors
    public DcMotorEx vslide_1, vslide_2;
    public DcMotorEx intake_slide;

    //states subclass
    public class States {
        //timers for finite state machine
        public ElapsedTime lift_time = new ElapsedTime();
        public ElapsedTime deposit_timer = new ElapsedTime();
        public ElapsedTime intake_slide_timer = new ElapsedTime();

        public SlideStates lift_state;
        public IntakeSlideStates intake_slide_state;
        public States() {
            lift_state = SlideStates.DOWN_REST;
            intake_slide_state = IntakeSlideStates.IN;
        }
    }

    //constants subclass
    @Config
    public static class Constants {
        //POSITION CONSTANTS

        public static int VSLIDES_UP = 2300;
        public static int VSLIDES_DOWN = 0;
        public static int INT_SLIDES_OUT = 1000;
        public static int INT_SLIDES_IN = 0;

        // TIMER CONSTANTS
        public static double LIFT_TIME = 1;
        public static double INTAKE_SLIDE_TIME = 1;
        public static double DEPOSIT_TIME = 0.5;
    }

    public MyRobot(HardwareMap hMap, Gamepad gamepad1, Gamepad gamepad2) {

        //init core stuff
        imu = hMap.get(IMU.class, "imu");
        imu_params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.RIGHT,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imu_params);
        state = new States();
        odo = hMap.get(GoBildaPinpointDriver.class, "odo");
        odo.setOffsets(0, 0);

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
