package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {
    // -------- GENERAL --------
    public static boolean UPDATE_ODO = true;

    // -------- CONTROL --------
    public static double TURN_COEFFICIENT = 0.6;    //note: makes turning less sensitive

    // -------- MOVEMENT --------
    public static int LIFT_TOLERANCE = 15;
    public static int LIFT_UP = 2300;
    public static int LIFT_DOWN = 0;

    public static int INTAKE_TOLERANCE = 15;
    public static int INTAKE_IN_POS = 0;
    public static int INTAKE_OUT_POS = 1000;

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

    // --------------------------------
    // -------- HARDWARE NAMES --------
    public static String NAME_PINPOINT = "pinpoint";
    public static String NAME_COLOURSENSOR = "colour_sensor";
    public static String NAME_IMU = "imu";
    public static String NAME_FR = "rightFront";
    public static String NAME_FL = "leftFront";
    public static String NAME_BR = "rightRear";
    public static String NAME_BL = "leftRear";
    public static String NAME_VSLIDE1 = "vslide_1";
    public static String NAME_VSLIDE2 = "Vslide_2";
    public static String NAME_INTSLIDE = "intake_slide";
    public static String NAME_INTAKE = "intake";
    public static String NAME_HANGL = "hang_left";
    public static String NAME_HANGR = "hang_right";
    public static String NAME_CLAW = "claw";
    public static String NAME_WRIST = "wrist";
    public static String NAME_ARML = "arm_left";
    public static String NAME_ARMR = "arm_right";
}
