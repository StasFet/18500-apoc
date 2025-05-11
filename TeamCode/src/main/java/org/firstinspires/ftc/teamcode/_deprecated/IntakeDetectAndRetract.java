package org.firstinspires.ftc.teamcode._deprecated;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.SubsystemStates;

// intake-only, does full intake stuff until transfer
public class IntakeDetectAndRetract extends CommandBase {
    MyRobot robot;
    Intake intake;

    public IntakeDetectAndRetract(Intake intake, MyRobot robot) {
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
            case EXTENDED:
                intake.setState(SubsystemStates.IntakeStates.SEARCHING_FOR_SAMPLE);
                break;
            case SEARCHING_FOR_SAMPLE:
                robot.intakeColorSensor.enableLed(true);
                intake.intakeOn();
                while (!intake.checkSample()) {}    // note: waits for sample to be detected
                intake.setState(SubsystemStates.IntakeStates.EJECTING);
                break;
            case EJECTING:
                robot.intakeColorSensor.enableLed(false);
                intake.intakeEject();
                while (intake.timer.milliseconds() <= 350) {}   //note: intake will eject for 350ms to prevent possession of 2
                intake.intakeOff();
                intake.setState(SubsystemStates.IntakeStates.RETRACTING);
                break;
            case RETRACTING:
                intake.contractSlides();
                intake.setState(SubsystemStates.IntakeStates.TRANSFER);
                break;
        }
    }

    @Override
    public boolean isFinished() {
        return (intake.state == SubsystemStates.IntakeStates.TRANSFER);
    }
}
