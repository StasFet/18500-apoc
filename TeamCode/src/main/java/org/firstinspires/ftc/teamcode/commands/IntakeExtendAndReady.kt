package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.state.SubsystemStates.*
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeExtendAndReady(val robot: Robot, val intake: Intake) : CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.intakeBusy = false
        intake.intakeOff()
    }

    override fun execute() {
        when (intake.state) {
            IntakeStates.IDLE -> intake.updateState(IntakeStates.ACTIVATED)
            IntakeStates.ACTIVATED -> intake.updateState(IntakeStates.EXTENDING)
            IntakeStates.EXTENDING -> {
                intake.extendSlides()
                if (intake.slidesAtTarget()) intake.updateState(IntakeStates.EXTENDED)
            }
            else -> {}
        }
    }

    override fun isFinished(): Boolean {
        if (intake.state == IntakeStates.EXTENDED) {
            intake.intakeBusy = false
            return true
        }
        return false
    }
}