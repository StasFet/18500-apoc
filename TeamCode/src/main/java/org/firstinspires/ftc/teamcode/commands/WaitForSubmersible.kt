package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.pedropathing.follower.Follower

class WaitForSubmersible(val follower: Follower): CommandBase() {
    override fun isFinished(): Boolean {
        val isXInRange = follower.pose.x in 30.0..50.0
        val isYInRange = follower.pose.y in 55.0..88.0
        val isHeadingInRange = follower.pose.heading in Math.toRadians(315.0)..Math.toRadians(360.0)

        return isXInRange && isYInRange && isHeadingInRange
    }
}