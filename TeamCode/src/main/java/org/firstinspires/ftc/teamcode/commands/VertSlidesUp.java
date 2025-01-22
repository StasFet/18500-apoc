package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.VertSlides;
import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;

public class VertSlidesUp extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private MyRobot.States robot_state;
    private final VertSlides g_vslides;
    private final MyRobot robot;

    public VertSlidesUp(VertSlides subsystem, MyRobot robot_p) {
        robot = robot_p;
        robot_state = robot.state;
        g_vslides = subsystem;
        addRequirements(g_vslides);
    }

    @Override
    public void initialize() {
        robot_state.lift_state = SlideStates.UP_ESCALATING;
        robot_state.lift_time.reset();
    }

    @Override
    public void execute() {
        if (robot_state.lift_time.seconds() < MyRobot.Constants.LIFT_TIME) {
            robot_state.lift_state = SlideStates.UP_ESCALATING;
        }
    }

    @Override
    public boolean isFinished() {
        // finishes if time threshold passed or state is already up
        if (robot_state.lift_time.seconds() > MyRobot.Constants.LIFT_TIME+0.1 || robot_state.lift_state == SlideStates.UP_REST) {        //0.1 to allow for a bit of error
            robot_state.lift_state = SlideStates.UP_REST;
            return true;
        }
        return false;
    }
}
