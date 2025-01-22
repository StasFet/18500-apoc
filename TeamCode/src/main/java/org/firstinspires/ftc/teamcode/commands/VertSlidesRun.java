package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.VertSlides;

public class VertSlidesRun extends CommandBase {
    VertSlides vs;

    public VertSlidesRun(VertSlides subsystem) {
        vs = subsystem;
        addRequirements(vs);
    }

    @Override
    public void execute() {
        vs.run();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
