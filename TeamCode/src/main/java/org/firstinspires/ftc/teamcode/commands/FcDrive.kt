package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive

class FcDrive(val dt: MecanumDrive): CommandBase() {
    init {
        addRequirements(dt)
    }

    override fun execute() {
        dt.fcDrive()
    }

    override fun isFinished(): Boolean {
        return false
    }
}