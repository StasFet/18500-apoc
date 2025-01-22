package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;
import org.firstinspires.ftc.teamcode.subsystems.VertSlides;

public class VertSlidesDown extends CommandBase {
    private final MyRobot robot;
    private MyRobot.States robot_state;

    public VertSlidesDown(MyRobot robot_p) {
        robot = robot_p;
        robot_state = robot.state;
    }

    @Override
    public void initialize() {
        robot_state.lift_state = SlideStates.DOWN_LOWERING;
        robot_state.lift_time.reset();
    }

    @Override
    public void execute() {
        if (robot_state.lift_time.seconds() < 1) {
            robot_state.lift_state = SlideStates.DOWN_LOWERING;
        }
    }

    @Override
    public boolean isFinished() {
        // finishes if passed time threshold or if state is already down
        if (robot_state.lift_time.seconds() > MyRobot.Constants.LIFT_TIME+0.1 || robot_state.lift_state == SlideStates.DOWN_REST) {
            robot_state.lift_state = SlideStates.DOWN_REST;
            return true;
        }
        return false;
    }
}
