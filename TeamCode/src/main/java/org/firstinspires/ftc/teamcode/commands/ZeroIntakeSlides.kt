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
        intake.slide.power = 0.8
    }

    override fun isFinished(): Boolean {
        return intake.touch.isPressed
    }

    override fun end(interrupted: Boolean) {
        intake.slide.power = 0.0
        intake.slide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        intake.slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }
}