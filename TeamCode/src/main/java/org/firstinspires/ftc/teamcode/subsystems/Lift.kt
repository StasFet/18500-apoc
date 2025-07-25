package org.firstinspires.ftc.teamcode.subsystems

import android.hardware.SensorPrivacyManager.Sensors
import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot
import kotlin.math.abs
import kotlin.math.min


@Config
class Lift(val robot: Robot) : SubsystemBase() {
    val right = robot.vslider
    val left = robot.vslidel
    val touch = robot.vSlideTouch

    val p = 6.0
    val i = 0.0
    val d = 0.02
    val f = 0.0

    var doIStopPID = true
    var doIStopBrake = true

    val pidf: PIDFController = PIDFController(p, i, d, f)

    init {
        right.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        left.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        right.mode = DcMotor.RunMode.RUN_USING_ENCODER
        left.mode = DcMotor.RunMode.RUN_USING_ENCODER

        right.targetPositionTolerance = LIFT_TOLERANCE.toInt()
        left.targetPositionTolerance = LIFT_TOLERANCE.toInt()

        pidf.setTolerance(LIFT_TOLERANCE)
    }

    fun customSetPoint(setPoint: Double) { pidf.setPoint = setPoint }
    fun contractionSetPoint() { customSetPoint(LIFT_DOWN) }
    fun extensionSetPoint() { customSetPoint(LIFT_UP) }
    fun specWallSetPoint() { customSetPoint(LIFT_SPECIMEN_WALL)}
    fun specBarSetPoint() { customSetPoint(LIFT_SPECIMEN_BAR)}

    fun setVelocity(vel: Double) {
        right.velocity = vel
        left.velocity = -vel
    }

    fun updateVelocity() {
        setVelocity(pidf.calculate(right.currentPosition.toDouble()))
    }

    fun setModes(mode: DcMotor.RunMode) {
        right.mode = mode
        left.mode = mode
    }

    fun setPowers(power: Double) {
        left.power = power
        right.power = -power
    }

    fun atSetPoint() = pidf.atSetPoint()

    fun brake(power: Double) {
//        left.targetPosition = left.currentPosition
//        right.targetPosition = right.currentPosition
//        setModes(DcMotor.RunMode.RUN_TO_POSITION)
//        setPowers(power)
        //setVelocity(0.0)

        var error = abs(LIFT_UP - right.currentPosition)
        var final = 0.0

        if (error > LIFT_TOLERANCE) {
            var kp = 0.00001
            final = min(abs(power), abs(error*kp))
        }

        setPowers(final)
    }

    fun brake(power: Double, offset: Int) {
//        left.targetPosition = left.currentPosition + offset
//        right.targetPosition = right.currentPosition + offset
//        setModes(DcMotor.RunMode.RUN_TO_POSITION)
//        setPowers(power)
        customSetPoint(right.currentPosition.toDouble())
    }

    fun convertToInt(value: Any): Int {
        if (value is Int) return value
        else if (value is Double) return value.toInt()
        else {throw ClassCastException()}
    }
}