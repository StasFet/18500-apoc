package org.firstinspires.ftc.teamcode._deprecated;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.state.SubsystemStates;

// intake-only, does full intake stuff until transfer
public class IntakeExtendAndReady extends CommandBase {
    MyRobot robot;
    Intake intake;

    public IntakeExtendAndReady(Intake intake, MyRobot robot) {
        this.intake = intake;
        this.robot = robot;

        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.intakeBusy = true;
        intake.intakeOff();
    }

    public void execute() {
        switch (intake.state) {
            case IDLE:
                intake.setState(SubsystemStates.IntakeStates.ACTIVATED);
                break;
            case ACTIVATED:
                intake.setState(SubsystemStates.IntakeStates.EXTENDING);
                break;
              case EXTENDING:
                intake.extendSlides();
                intake.setState(SubsystemStates.IntakeStates.EXTENDED);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        if(intake.state == SubsystemStates.IntakeStates.EXTENDED) {
            intake.intakeBusy = false;
            return true;
        }
        return false;
    }
}
