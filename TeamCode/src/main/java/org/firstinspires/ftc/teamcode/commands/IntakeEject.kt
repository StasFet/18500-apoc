package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Constants.INTAKE_EJECT_POS
import org.firstinspires.ftc.teamcode.core.Constants.INTAKE_WRIST_DOWN
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeEject(val intake: Intake): CommandBase() {

    override fun initialize() {
        intake.isEjecting = true
        //intake.wristToPos(INTAKE_EJECT_POS)
    }

    override fun isFinished() : Boolean {
        return intake.state != SubsystemStates.IntakeStates.ACTIVATED
    }

    override fun end(interrupted: Boolean) {
        //intake.wristToPos(INTAKE_WRIST_DOWN)
        intake.isEjecting = false
    }
}