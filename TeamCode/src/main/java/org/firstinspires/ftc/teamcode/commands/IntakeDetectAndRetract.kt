package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates.*
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
            IntakeStates.EXTENDED_AND_DOWN -> {
                intake.state = IntakeStates.SEARCHING_FOR_SAMPLE
            }
            IntakeStates.SEARCHING_FOR_SAMPLE -> {
                robot.intakeColorSensor.enableLed(true)
                intake.intakeOn()
                if (intake.checkSample()) intake.state = IntakeStates.EJECTING
            }
            IntakeStates.EJECTING -> {
                robot.intakeColorSensor.enableLed(false)
                intake.intakeEject()
                intake.intakeOff()
                if (intake.timer.seconds() > 0.5) intake.state = IntakeStates.RETRACTING
            }
            IntakeStates.RETRACTING -> {
                intake.contractSlides()
                if (intake.slidesAtTarget()) intake.state = IntakeStates.TRANSFER
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