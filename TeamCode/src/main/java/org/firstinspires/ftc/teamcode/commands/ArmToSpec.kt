package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Constants.ARM_SPEC
import org.firstinspires.ftc.teamcode.core.Constants.CLAW_OPEN
import org.firstinspires.ftc.teamcode.core.Robot

class ArmToSpec(val robot: Robot) : CommandBase() {
    override fun initialize() {
        robot.claw.position = CLAW_OPEN
        robot.armLeft.position = 1 - ARM_SPEC
        robot.armRight.position = ARM_SPEC
    }

    override fun isFinished(): Boolean {
        return true
    }
}