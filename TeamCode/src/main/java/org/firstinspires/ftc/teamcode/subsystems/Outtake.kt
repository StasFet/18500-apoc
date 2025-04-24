package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot

class Outtake(val robot: Robot): SubsystemBase() {

    val claw = robot.claw;
    val wrist = robot.wrist;
    val armLeft = robot.armLeft;
    val armRight = robot.armRight;

    fun clawOpen() {
        claw.position = CLAW_OPEN
    }
}