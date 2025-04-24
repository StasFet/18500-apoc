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
    public static int LIFT_TOLERANCE = 15;
    public static int LIFT_UP = 2300;
    public static int LIFT_DOWN = 0;

    public static double INTAKE_TOLERANCE = 15;
    public static double INTAKE_IN_POS = 0;
    public static double INTAKE_OUT_POS = 1000;

    public static double HANG_UP_POWER = 0.5;
    public static double HANG_RETRACT_TIME = 1000;         // note: in ms
    public static double HANG_EXTEND_TIME = 1000;

    public static double CLAW_OPEN = 1;
    public static double CLAW_CLOSED = 0;
    public static double WRIST_SPEC_WALL = 0;
    public static double WRIST_SPEC_BAR = 0;
    public static double WRIST_TRANSFER = 0;
    public static double ARM_IDLE = 0;
    public static double ARM_TRANSFER = 0;
    public static double ARM_RISE = 0;
    public static double ARM_DEPOSIT = 0;
    public static double ARM_OFFSET = 0;

    // -------- COLOUR SENSOR --------
    public static double YELLOW_RED_MIN = 150;
    public static double YELLOW_GREEN_MIN = 150;
    public static double RED_MIN = 150;
    public static double BLUE_MIN = 150;
    public static double OTHER_COLOUR_MAX = 100;

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
    public static String NAME_VSLIDE1 = "vslideLeft";
    public static String NAME_VSLIDE2 = "vslideRight";
    public static String NAME_INTSLIDE = "hslide";
    public static String NAME_INTAKE = "intake";
    public static String NAME_HANGL = "hang_left";
    public static String NAME_HANGR = "hang_right";
    public static String NAME_CLAW = "claw";
    public static String NAME_WRIST = "wrist";
    public static String NAME_ARML = "arm_left";
    public static String NAME_ARMR = "arm_right";
    public static String NAME_HANGRIGHT = "hangRight";
    public static String NAME_HANGLEFT = "hangLeft";
}
