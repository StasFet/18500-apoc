package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.hardware.ams.AMSColorSensor.Wait
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.*
import kotlin.math.abs

class PrepForTransfer(val intake: Intake, val outtake: Outtake) : CommandBase() {


    init {
        addRequirements(intake, outtake)
    }


    override fun initialize() {
            intake.brake()
            outtake.state = SubsystemStates.OuttakeState.WAITING_FOR_TRANSFER
            intake.runToPos(INTAKE_TRANSFER_POS, 1.0)
            intake.wristToPos(INTAKE_WRIST_TRANSFER)
            outtake.setPosition(ARM_TRANSFER)
            outtake.setLinkagePos(ARM_LINK_TRANSFER)
            outtake.clawOpen()
            intake.openIntakeStopper()
    }

    override fun isFinished(): Boolean {
        return abs(intake.slide.currentPosition - INTAKE_TRANSFER_POS) < 10
    }

    override fun end(interrupted: Boolean) {
        intake.brake()
    }
}