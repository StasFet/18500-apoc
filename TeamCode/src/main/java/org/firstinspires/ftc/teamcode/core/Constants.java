package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;

@Config
public class Constants {
    //general constants
    public static boolean UPDATE_ODO = true;

    //control constants
    public static double TURN_COEFFICIENT = 0.6;    //note: makes turning less sensitive

    //movement constants
    public static int LIFT_TOLERANCE = 15;
    public static int LIFT_UP = 2300;
    public static int LIFT_DOWN = 0;

    public static int INTAKE_TOLERANCE = 15;
    public static int INTAKE_IN_POS = 0;
    public static int INTAKE_OUT_POS = 0;
}
