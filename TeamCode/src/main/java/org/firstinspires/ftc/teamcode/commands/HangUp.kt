package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Hang

class HangUp(val hang: Hang, val robot: Robot) : CommandBase() {
    init {
        addRequirements(hang)
    }

    var leftPos: Double = 0.0
    var rightPos: Double = 0.0
    var leftLastPos: Double = leftPos
    var rightLastPos: Double = rightPos

    override fun initialize() {
        hang.state = SubsystemStates.HangStates.EXTENDING
    }

    override fun execute() {
        leftPos = hang.leftEncoder.voltage / 3.3
        rightPos = hang.rightEncoder.voltage / 3.3

        if (hang.leftRevolutions < 9) hang.setLeftPower(-1.0)
        if (hang.rightRevolutions < 9) hang.setRightPower(-1.0)

        if (leftPos <= 0.3 && leftLastPos >= 0.7) hang.leftRevolutions++
        if (rightPos <= 0.3 && rightLastPos >= 0.7) hang.rightRevolutions++
        leftLastPos = leftPos
        rightLastPos = rightPos
    }

    override fun isFinished(): Boolean {
        if (hang.leftRevolutions > 9 && hang.rightRevolutions > 9) {
            hang.state = SubsystemStates.HangStates.EXTENDED
            return true
        }
        return false
    }
}