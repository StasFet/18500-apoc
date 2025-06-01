package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.subsystems.Intake

class ZeroIntakeSlides(val intake: Intake) : CommandBase() {
    init {
        addRequirements(intake)
    }

    override fun initialize() {
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
        intake.slide.power = -0.5
    }

    override fun isFinished(): Boolean {
        return intake.slide.getCurrent(CurrentUnit.MILLIAMPS) >= 6000
    }

    override fun end(interrupted: Boolean) {
        intake.slide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }
}