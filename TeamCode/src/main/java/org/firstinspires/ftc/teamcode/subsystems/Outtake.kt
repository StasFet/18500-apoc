package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot

class Outtake(val robot: Robot): SubsystemBase() {

    val claw = robot.claw;
    val armLeft = robot.armLeft;
    val armRight = robot.armRight;

    public fun clawOpen() { claw.position = CLAW_OPEN }
    public fun clawClose() { claw.position = CLAW_CLOSED }

    public fun setPosition(pos: Double) {
        armLeft.position = 1-pos+ARM_OFFSET
        armRight.position = pos
    }
}