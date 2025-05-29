package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeRetract(val intake: Intake) : CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.state = SubsystemStates.IntakeStates.RETRACTING
        intake.contractionSetPoint()
        intake.intakeOff()
        intake.wristToPos(INTAKE_WRIST_TRANSFER)
    }

    override fun execute() {
        intake.updateVelocity()
    }

    override fun isFinished(): Boolean {
        intake.slide.power = 0.001
        if (intake.slidesAtSetPoint()) {
            intake.state = SubsystemStates.IntakeStates.IDLE
            return true
        }
        return false
    }
}