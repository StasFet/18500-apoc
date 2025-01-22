package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.state.intake.IntakeSlideStates;

import org.firstinspires.ftc.teamcode.core.MyRobot;

public class IntakeSlides extends SubsystemBase {
    DcMotorEx motor;
    MyRobot robot;
    MyRobot.States robot_state;
    PIDController pid = new PIDController(0, 0, 0);

    public IntakeSlides(MyRobot robot) {
        this.robot = robot;
        motor = robot.intake_slide;
    }

    public void run() {
        switch (robot_state.intake_slide_state) {
            case IN: case MOVING_IN:
                motor.setPower(pid.calculate(motor.getCurrentPosition(), MyRobot.Constants.INT_SLIDES_IN));
                break;
            case OUT: case MOVING_OUT:
                motor.setPower(pid.calculate(motor.getCurrentPosition(), MyRobot.Constants.INT_SLIDES_OUT));
                break;
        }
    }
}
