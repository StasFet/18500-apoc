package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Intake

class IntakeExtend(val intake: Intake) : CommandBase() {

    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
        intake.state = SubsystemStates.IntakeStates.EXTENDING
        intake.extensionSetPoint()
        intake.wristToPos(INTAKE_HOVER)
        intake.state = SubsystemStates.IntakeStates.EXTENDED
        //intake.intakeOff()
    }

    override fun execute() {
        intake.updateVelocity()
    }

    override fun isFinished(): Boolean {
        if (intake.slidesAtSetPoint()) {
            intake.state = SubsystemStates.IntakeStates.EXTENDED
            //intake.wristToPos(INTAKE_WRIST_DOWN)
            return true
        }
        return false
    }

    override fun end(interrupted: Boolean) {
        intake.brake()
    }
}