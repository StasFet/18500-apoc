package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot
import kotlin.math.abs

@Config
class Hang(val robot: Robot) : SubsystemBase() {

    val left = robot.hangLeft
    val right = robot.hangRight

    val leftEncoder: AnalogInput = robot.hangLeftEncoder
    val rightEncoder: AnalogInput = robot.hangRightEncoder

    public fun retract() {

    }

    public fun extend() {
    }

    private fun moveDegrees(degrees: Int) {
        
    }
}