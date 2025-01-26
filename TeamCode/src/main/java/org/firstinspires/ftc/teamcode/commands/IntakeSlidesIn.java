package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.state.intake.IntakeSlideStates;

public class IntakeSlidesIn extends CommandBase {
    private final MyRobot robot;
    private MyRobot.States robot_state;

    public IntakeSlidesIn(MyRobot robot_p) {
        robot = robot_p;
        robot_state = robot.state;
    }

    @Override
    public void initialize() {
        robot_state.intake_slide_state = IntakeSlideStates.MOVING_IN;
        robot_state.intake_slide_timer.reset();
    }

    @Override
    public void execute() {
        if (robot_state.intake_slide_timer.seconds() < MyRobot.Constants.INTAKE_SLIDE_TIME) {
            robot_state.intake_slide_state = IntakeSlideStates.MOVING_IN;
        }
    }

    @Override
    public boolean isFinished() {
        // finishes if passed time threshold or if state is already out
        if (robot_state.intake_slide_timer.seconds() > MyRobot.Constants.INTAKE_SLIDE_TIME || robot_state.intake_slide_state == IntakeSlideStates.IN) {
            robot_state.intake_slide_state = IntakeSlideStates.IN;
            return true;
        }
        return false;
    }
}
