package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.subsystems.Lift

class AlwaysUpdateLiftPID(val lift: Lift) : CommandBase() {
    override fun execute() {
        lift.updateVelocity()
    }

    override fun isFinished(): Boolean {
        return false
    }
}