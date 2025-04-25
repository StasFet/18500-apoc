package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.state.SubsystemStates.*
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeDetectAndRetract(val intake: Intake, val robot: Robot): CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.intakeBusy = true
        intake.intakeOff()
    }

    override fun execute() {
        when (intake.state) {
            IntakeStates.EXTENDED -> {
                intake.state = IntakeStates.SEARCHING_FOR_SAMPLE
            }
            IntakeStates.SEARCHING_FOR_SAMPLE -> {
                robot.intakeColorSensor.enableLed(true)
                intake.intakeOn()
                if (intake.checkSample()) intake.updateState(IntakeStates.EJECTING)
            }
            IntakeStates.EJECTING -> {
                robot.intakeColorSensor.enableLed(false)
                intake.intakeEject()
                intake.intakeOff()
                intake.updateState(IntakeStates.RETRACTING)
            }
            IntakeStates.RETRACTING -> {
                intake.contractSlides()
                if (intake.slidesAtTarget()) intake.updateState(IntakeStates.TRANSFER)
            }
            else -> {}
        }
    }

    override fun isFinished(): Boolean {
        if (intake.state == IntakeStates.TRANSFER) {
            intake.intakeBusy = false
            return true
        }
        return false
    }
}