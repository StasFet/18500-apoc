package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {

    // -------- GENERAL --------
    public static boolean UPDATE_ODO = true;

    // -------- DRIVE --------
    public static double TURN_COEFFICIENT = 0.6;    //note: makes turning less
    public static double FRONT_DT_MULTI = 1.0;
    public static double REAR_DT_MULTI = 1.0;
    public static double CACHE_THRESHOLD = 0.01;

    // -------- MOVEMENT --------

    // lift
    public static double LIFT_TOLERANCE = 15;
    public static double LIFT_UP = -800; //has been tested
    public static double LIFT_DOWN = 15; //has been tested

    // intake
    public static double INTAKE_TOLERANCE = 30;
    public static double INTAKE_IN_POS = 30; //has been tested
    public static double INTAKE_TRANSFER_POS = -100; //has been tested
    public static double INTAKE_OUT_POS = -600; //has been tested
    public static double INTAKE_EJECT_POS = 0.3;
    public static double INTAKE_WRIST_DOWN = 0.2;
    public static double INTAKE_WRIST_TRANSFER = 0.28;
    public static double INTAKE_STOP_OPEN = 0.55; //has been changed
    public static double INTAKE_STOP_CLOSED = 1; //hsa been changed
    public static double INTAKE_STALL_CURRENT_MA = 5000;
    public static double INTAKE_SLIDE_STALL_CURRENT_MA = 6000;
    public static double INTAKE_EJECT_PWR = 1.0;
    public static double INTAKE_PWR = -1.0;
    public static double INTAKE_START_POSITION = 0.58;
    public static double INTAKE_HOVER = 0.35;

    // hang
    public static double HANG_REV_UP = 9;
    public static double HANG_REV_HANGING = 5;
    public static double HANG_POWER_COEFF = 0.9;


    // outtake
    public static double CLAW_OPEN = 0.4; //has been tested
    public static double CLAW_CLOSED = 0.2; //has been tested
    public static double ARM_SPEC_WALL = 0.07;
    public static double LINKAGE_SPEC_WALL = 0.07;
    public static double ARM_SPEC_BAR = 0.82;
    public static double ARM_SPEC = 0.82;
    public static double ARM_TRANSFER = 0.88;
    //public static double ARM_LOW_BAR = 0.7;
    public static double ARM_DEPOSIT_SAMPLE = 0.3;
    public static double ARM_DEPOSIT_SPECIMEN = 0.82;
    public static double ARM_LINK_OUT = 0.08;
    public static double ARM_LINK_TRANSFER = 0.75;
    public static double ARM_LINK_IN = 0.9;
    public static double ARM_START = 0.2;
    public static double VSLIDES_SPECIMEN_DEPOSIT = 500;
    public static double LINKAGE_SPEC_BAR = 0.07;

    // -------- COLOUR SENSOR --------
    public static int[] CS_RED_RGB = {630, 335, 153};
    public static int[] CS_RED_TOLERANCE = {175, 90, 55};

    public static int[] CS_YELLOW_RGB = {1075, 1300, 270};
    public static int[] CS_YELLOW_TOLERANCE = {260, 325, 60};

    public static int[] CS_BLUE_RGB = {125, 252, 605};
    public static int[] CS_BLUE_TOLERANCE = {35, 60, 135};

// -----------------------------------------------------------------------------
    // -------- HARDWARE NAMES --------
    public static String NAME_PINPOINT = "pinpoint";
    public static String NAME_COLOURSENSOR = "colour_sensor";
    public static String NAME_IMU = "imu";
    public static String NAME_PTO = "PTO";
    public static String NAME_FR = "fr";
    public static String NAME_FL = "fl";
    public static String NAME_BR = "br";
    public static String NAME_BL = "bl";
    public static String NAME_VSLIDEL = "vslideLeft";
    public static String NAME_VSLIDER = "vslideRight";
    public static String NAME_INTSLIDE = "hslide";
    public static String NAME_INTAKE = "intake";
    public static String NAME_INTAKE_STOP = "intakeStop";   // 0.53 - open, 0.02 - closed
    public static String NAME_HANGL = "hang_left";
    public static String NAME_HANGR = "hang_right";
    public static String NAME_CLAW = "claw";
    public static String NAME_ARM_LINKAGE = "armLinkage";
    public static String NAME_ARML = "arm_left";
    public static String NAME_ARMR = "arm_right";
    public static String NAME_HANGRIGHT = "hangRight";
    public static String NAME_HANGLEFT = "hangLeft";
    public static String NAME_HANGRIGHTENC = "hangRightEncoder";
    public static String NAME_HANGLEFTENC = "hangLeftEncoder";
    public static String NAME_INTAKERIGHT = "intakeRight";
    public static String NAME_INTAKELEFT = "intakeLeft";
    public static String NAME_HSLIDETOUCH = "hSlideTouch";
    public static String NAME_VSLIDETOUCH = "vSlideTouch";
}
