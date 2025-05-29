package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeWaitForSample(val intake: Intake) : CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.state = SubsystemStates.IntakeStates.ACTIVATED
        intake.closeIntakeStopper()
        intake.intakeOn()
    }

    override fun execute() {
        intake.updateVelocity()
        if (!intake.isEjecting) intake.intakeOn()
        else intake.intakeEject()
    }

    override fun isFinished(): Boolean {
        if (intake.checkColour() !in intake.cols && intake.checkColour() != Intake.Colours.NONE) {
            intake.openIntakeStopper()
            return false
        } else if (intake.checkColour() in intake.cols) {
            intake.closeIntakeStopper()
            intake.state = SubsystemStates.IntakeStates.HOLDING_SAMPLE
            return true
        } else {
            intake.closeIntakeStopper()
            return false
        }
    }
}