package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.WaitCommand
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Outtake

class IntakeRetract(val intake: Intake, val outtake: Outtake) : CommandBase() {

    init {
        addRequirements(intake, outtake)
    }

    override fun initialize() {
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
        intake.state = SubsystemStates.IntakeStates.RETRACTING
        intake.customSetPoint(INTAKE_TRANSFER_POS)
        intake.wristToPos(INTAKE_WRIST_TRANSFER)
        intake.intakeOff()
    }

    override fun execute() {
        intake.updateVelocity()
    }

    override fun isFinished(): Boolean {
        return intake.slidesAtSetPoint()
    }

    override fun end(interrupted: Boolean) {
        //if (intake.slide.currentPosition.toDouble() - INTAKE_TRANSFER_POS <= 30 || intake.slide.currentPosition.toDouble() - INTAKE_TRANSFER_POS >= -30) {
            intake.state = SubsystemStates.IntakeStates.TRANSFER
            outtake.state = SubsystemStates.OuttakeState.WAITING_FOR_TRANSFER
            intake.brake()
        //}
    }
}