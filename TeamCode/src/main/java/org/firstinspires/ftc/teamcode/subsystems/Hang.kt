package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot

class Hang(val robot: Robot) : SubsystemBase() {

    val left = robot.hangLeft
    val right = robot.hangRight

    public fun retract() {
        val timer: ElapsedTime = ElapsedTime()
        timer.reset()

        while (timer.milliseconds() < HANG_RETRACT_TIME) {
            left.set(HANG_UP_POWER)
            right.set(HANG_UP_POWER)
        }
    }

    public fun extend() {
        val timer: ElapsedTime = ElapsedTime()
        timer.reset()

        while (timer.milliseconds() < HANG_EXTEND_TIME) {
            left.set(-HANG_UP_POWER)
            right.set(-HANG_UP_POWER)
        }
    }
}