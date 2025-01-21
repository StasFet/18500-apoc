package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.VertSlides;

import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;

public class VertSlidesToggle extends CommandBase {
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
    private final VertSlides g_vslides;
    private final MyRobot robot;

    public VertSlidesToggle(VertSlides subsystem, MyRobot robot_p) {
        robot = robot_p;
        g_vslides = subsystem;
        addRequirements(g_vslides);
    }

    @Override
    public void initialize() {
        switch (robot.state.slide_state) {
            case UP:
                g_vslides.setSlidePositionByState(SlideStates.DOWN);
                robot.state.slide_state = SlideStates.DOWN;     //just in case lol
                break;
            case DOWN:
                g_vslides.setSlidePositionByState(SlideStates.UP);
                robot.state.slide_state = SlideStates.UP;       //just in case lol
                break;
        }
    }
}
