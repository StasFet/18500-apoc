package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.subsystems.Intake
import org.firstinspires.ftc.teamcode.subsystems.Lift

class ZeroVerticalSlides(val lift: Lift): CommandBase() {
    init {
        addRequirements(lift)
    }

    override fun initialize() {
        lift.doIStopPID = true
        lift.doIStopBrake = true
        lift.setModes(DcMotor.RunMode.RUN_USING_ENCODER)
        lift.setPowers(-0.8)
    }

    override fun isFinished() = lift.touch.isPressed

    override fun end(interrupted: Boolean) {
        lift.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        lift.setModes(DcMotor.RunMode.RUN_USING_ENCODER)
        lift.setPowers(0.0)
    }
}