package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates.*
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.core.Constants.*

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
            IntakeStates.IDLE -> intake.state = IntakeStates.ACTIVATED
            IntakeStates.ACTIVATED -> intake.state = IntakeStates.EXTENDING
            IntakeStates.EXTENDING -> {
                intake.extendSlides()
                if (intake.slidesAtTarget()) intake.state = IntakeStates.EXTENDED
            }
            IntakeStates.EXTENDED -> {
                intake.wristToPos(INTAKE_WRIST_DOWN)
                intake.state = IntakeStates.EXTENDED_AND_DOWN
            }
            else -> {}
        }
    }

    override fun isFinished(): Boolean {
        if (intake.state == IntakeStates.EXTENDED_AND_DOWN) {
            intake.intakeBusy = false
            return true
        }
        return false
    }
}