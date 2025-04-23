package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive

class Drive(val dt: MecanumDrive, val robot: Robot) : CommandBase() {
    init { addRequirements(dt) }

    override fun execute() = dt.rcDrive()
    override fun isFinished(): Boolean = false
}