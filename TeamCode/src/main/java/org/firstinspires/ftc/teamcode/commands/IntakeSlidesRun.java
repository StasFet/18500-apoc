package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.IntakeSlides;

public class IntakeSlidesRun extends CommandBase {
    private final IntakeSlides is;

    public IntakeSlidesRun(IntakeSlides subsystem) {
        is = subsystem;
        addRequirements(is);
    }

    @Override
    public void execute() {
        is.run();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
