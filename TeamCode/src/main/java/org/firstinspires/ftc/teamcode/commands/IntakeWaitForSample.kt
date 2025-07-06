package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.WaitCommand
import org.firstinspires.ftc.teamcode.core.Constants.INTAKE_EJECT_POS
import org.firstinspires.ftc.teamcode.core.Constants.INTAKE_WRIST_DOWN
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeWaitForSample(val intake: Intake) : CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.state = SubsystemStates.IntakeStates.ACTIVATED
        intake.wristToPos(INTAKE_WRIST_DOWN)
        intake.closeIntakeStopper()
        intake.intakeOn()
        intake.brake()
    }

    override fun execute() {
        if (intake.isEjecting || intake.isStalling()) intake.intakeEject()
        else intake.intakeOn()
    }

    override fun isFinished(): Boolean {
        if (intake.checkColour() !in intake.cols && intake.checkColour() != Intake.Colours.NONE) {
            intake.wristToPos(INTAKE_EJECT_POS)
            intake.intakeEject()
            //intake.openIntakeStopper()
            return false
        } else if (intake.checkColour() in intake.cols) {
            intake.wristToPos(INTAKE_WRIST_DOWN)
            intake.intakeOn()
            intake.closeIntakeStopper()
            intake.state = SubsystemStates.IntakeStates.HOLDING_SAMPLE
            return true
        } else {
            intake.wristToPos(INTAKE_WRIST_DOWN)
            intake.intakeOn()
            intake.closeIntakeStopper()
            return false
        }
    }
}