package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.core.MyRobot;


public class DriveTrain extends SubsystemBase {
    private final DcMotorEx front_left;
    private final DcMotorEx front_right;
    private final DcMotorEx back_left;
    private final DcMotorEx back_right;

    private final GamepadEx gamepad;
    IMU g_imu;

    public DriveTrain(final MyRobot robot) {
        front_left = robot.fl;
        front_right = robot.fr;
        back_left = robot.bl;
        back_right = robot.br;

        gamepad = robot.gp_drive;
        g_imu = robot.imu;

        front_right.setDirection(DcMotorEx.Direction.REVERSE);
        back_right.setDirection(DcMotorEx.Direction.REVERSE);

        //set all encoder to 0
        front_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        front_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        back_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //motors will use built in pid tuning 
        front_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        front_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        back_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void run() {
        double y = -gamepad.getLeftY();
        double x = gamepad.getLeftX();
        double rx = gamepad.getRightX();

        double bot_heading = g_imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
    }

}
