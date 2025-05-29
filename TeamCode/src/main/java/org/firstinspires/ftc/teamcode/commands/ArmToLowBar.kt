package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Constants.ARM_LOW_BAR
import org.firstinspires.ftc.teamcode.core.Constants.ARM_SPEC
import org.firstinspires.ftc.teamcode.core.Constants.CLAW_CLOSED
import org.firstinspires.ftc.teamcode.core.Constants.CLAW_OPEN
import org.firstinspires.ftc.teamcode.core.Robot

class ArmToLowBar(val robot: Robot) : CommandBase() {
    override fun initialize() {
        robot.claw.position = CLAW_CLOSED
        robot.armLeft.position = 1 - ARM_LOW_BAR
        robot.armRight.position = ARM_LOW_BAR
    }

    override fun isFinished(): Boolean {
        return true
    }
}