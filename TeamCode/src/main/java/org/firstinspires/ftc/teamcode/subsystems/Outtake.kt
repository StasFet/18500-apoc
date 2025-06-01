package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates

class Outtake(val robot: Robot): SubsystemBase() {

    val claw = robot.claw;
    val armLeft = robot.armLeft;
    val armRight = robot.armRight;
    var state = SubsystemStates.OuttakeState.IDLE

    fun clawOpen() { claw.position = CLAW_OPEN }
    fun clawClose() { claw.position = CLAW_CLOSED }

    public fun setPosition(pos: Double) {
        armLeft.position = 1-pos
        armRight.position = pos
    }
}