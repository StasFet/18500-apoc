package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.arcrobotics.ftclib.hardware.motors.Motor.Encoder
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import kotlin.math.abs

@Config
class Hang(val robot: Robot) : SubsystemBase() {

    val left = robot.hangLeft
    val right = robot.hangRight
    val leftEncoder: AnalogInput = robot.hangLeftEncoder
    val rightEncoder: AnalogInput = robot.hangRightEncoder

    public var state: SubsystemStates.HangStates = SubsystemStates.HangStates.DOWN
    public var leftRevolutions = 0;
    public var rightRevolutions = 0;

    public fun setPowersTo(power: Double) {
        setLeftPower(power)
        setRightPower(power)
    }

    public fun setLeftPower(power: Double) {
        left.set(HANG_POWER_COEFF * power)
    }

    public fun setRightPower(power: Double) {
        right.set(HANG_POWER_COEFF * power)
    }

}