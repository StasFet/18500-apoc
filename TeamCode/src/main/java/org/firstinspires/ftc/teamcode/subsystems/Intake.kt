package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.arcrobotics.ftclib.hardware.ServoEx
import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.*
import org.firstinspires.ftc.teamcode.core.Constants.*

@Config
public class Intake(val robot: Robot) : SubsystemBase() {
    val intake: DcMotorEx = robot.intakeSpin
    val slide: DcMotorEx = robot.intakeSlide
    val leftServo: ServoEx = robot.intakeLeft
    val rightServo: ServoEx = robot.intakeRight
    val colourSensor: RevColorSensorV3 = robot.intakeColorSensor
    val timer: ElapsedTime = ElapsedTime()

    val dashboard: FtcDashboard = FtcDashboard.getInstance()

    var state = SubsystemStates.IntakeStates.IDLE
        set(value) = updateState(value)
    var intakeColour: IntakeColour = robot.intakeColor
    var intakeBusy = false

    val Kp = 1.0
    val Ki = 0.0
    val Kd = 0.1;
    val Kf = 0.0001;

    private val pidf: PIDFController = PIDFController(Kp, Ki, Kd, Kf)

    init {
        pidf.setTolerance(INTAKE_TOLERANCE)
        timer.reset()
    }

    // inaccurate name but idk what else to call it
    public fun slidesAtTarget(): Boolean {
        slide.velocity = pidf.calculate(slide.currentPosition.toDouble())
        return pidf.atSetPoint()
    }

    fun contractSlides() { pidf.setPoint = INTAKE_IN_POS }
    fun extendSlides() { pidf.setPoint = INTAKE_OUT_POS }

    fun intakeOn() { intake.power = 1.0 }
    fun intakeOff() { intake.power = 0.0 }
    fun intakeEject() { intake.power = -1.0 }

    fun wristToPos(pos: Double) {
        leftServo.position = pos
        rightServo.position = 1 - pos + INTAKE_WRIST_OFFSET
    }

    fun updateState(state: SubsystemStates.IntakeStates) {
        this.state = state
        timer.reset()
    }

    public fun checkSample() : Boolean {
        val y = detectColour(Colours.YELLOW)
        val b = detectColour(Colours.BLUE)
        val r = detectColour(Colours.RED)

        when (intakeColour) {
            IntakeColour.YELLOW -> return y
            IntakeColour.BLUE -> return b
            IntakeColour.RED -> return r
            IntakeColour.BLUEYELLOW -> return b || y
            IntakeColour.REDYELLOW -> return r || y
            else -> return false
        }
    }

    private fun detectColour(color: Colours): Boolean {
        val r = colourSensor.red()
        val g = colourSensor.green()
        val b = colourSensor.blue()
        when (color) {
            Colours.YELLOW -> if (r > YELLOW_RED_MIN && g > YELLOW_GREEN_MIN) return true
            Colours.BLUE -> if (b > BLUE_MIN && g < OTHER_COLOUR_MAX && r < OTHER_COLOUR_MAX) return true
            Colours.RED -> if (r > RED_MIN && b < OTHER_COLOUR_MAX && g < OTHER_COLOUR_MAX) return true
        }
        return false
    }

    enum class IntakeColour {
        RED,
        BLUE,
        REDYELLOW,
        BLUEYELLOW,
        YELLOW
    }

    enum class Colours {
        RED,
        BLUE,
        YELLOW
    }

}