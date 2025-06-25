package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeRetract(val intake: Intake) : CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
        intake.state = SubsystemStates.IntakeStates.RETRACTING
        intake.customSetPoint(INTAKE_TRANSFER_POS)
        intake.intakeOff()
        intake.wristToPos(INTAKE_WRIST_TRANSFER)
    }

    override fun execute() {
        intake.updateVelocity()
    }

    override fun isFinished(): Boolean {
        return intake.slidesAtSetPoint()
    }

    override fun end(interrupted: Boolean) {
        intake.state = SubsystemStates.IntakeStates.TRANSFER
        intake.brake()
    }
}