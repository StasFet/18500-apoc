package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.subsystems.Lift

class LiftUp(val lift: Lift) : CommandBase() {
    init {
        addRequirements(lift)
    }

    override fun initialize() {
        lift.pidf.p = 4.0
        lift.setModes(DcMotor.RunMode.RUN_USING_ENCODER)
        lift.extensionSetPoint()
    }

    override fun execute() {
        lift.updateVelocity()
    }

    override fun isFinished(): Boolean {
        return lift.atSetPoint()
    }

    override fun end(interrupted: Boolean) {
        lift.brake(0.5)
    }
}