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
    public static int INTAKE_OUT_POS = 0;

    // -------- COLOUR SENSOR --------
    public static double YELLOW_RED_MIN = 150;
    public static double YELLOW_GREEN_MIN = 150;
    public static double RED_MIN = 150;
    public static double BLUE_MIN = 150;
    public static double OTHER_COLOUR_MAX = 100;

}
