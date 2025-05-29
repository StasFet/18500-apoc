package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.arcrobotics.ftclib.hardware.ServoEx
import com.acmerobotics.dashboard.FtcDashboard
import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.core.*
import org.firstinspires.ftc.teamcode.core.Constants.*
import kotlin.math.abs

@Config
public class Intake(val robot: Robot) : SubsystemBase() {

    var p = PIDTune.P
    var i = PIDTune.I
    var d = PIDTune.D

    val intake: DcMotorEx = robot.intakeSpin
    val slide: DcMotorEx = robot.intakeSlide
    val leftServo: Servo = robot.intakeLeft
    val rightServo: Servo = robot.intakeRight
    val colourSensor: RevColorSensorV3 = robot.intakeColorSensor
    val intakeStopper: Servo = robot.intakeStopper;
    val timer: ElapsedTime = ElapsedTime()
    val cols = robot.cols
    var isEjecting = false
    var cachedPower = 0.0

    val dashboard: FtcDashboard = FtcDashboard.getInstance()

    var state = SubsystemStates.IntakeStates.IDLE
    var intakeBusy = false

    val controller: PIDController = PIDController(p, i, d)

    init {
        colourSensor.initialize()
        slide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
        slide.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        controller.setTolerance(INTAKE_TOLERANCE)
        timer.reset()
    }

    fun updateVelocity() {
        //controller.setPID(p, i, d)
        slide.velocity = controller.calculate(slide.currentPosition.toDouble())
    }

    fun slidesAtSetPoint(): Boolean = controller.atSetPoint()

    fun contractionSetPoint() { controller.setPoint = INTAKE_IN_POS }
    fun extensionSetPoint() { controller.setPoint = INTAKE_OUT_POS }
    fun customSetPoint(setPoint: Double) { controller.setPoint = setPoint }

    fun intakeOn() {
        if (cachedPower != -1.0) {
            intake.power = -1.0
            cachedPower = -1.0
        }
    }
    fun intakeOff() {
        if (cachedPower != 0.0) {
            intake.power = 0.0
            cachedPower = 0.0
        }
    }
    fun intakeEject() {
        if (cachedPower != 0.3) {
            intake.power = 0.3
            cachedPower = 0.3
        }
    }

    fun openIntakeStopper() {
        intakeStopper.position = INTAKE_STOP_OPEN;
    }

    fun closeIntakeStopper() {
        intakeStopper.position = INTAKE_STOP_CLOSED;
    }

    fun wristToPos(pos: Double) {
        leftServo.position = 1 - pos
        rightServo.position = pos
    }

    fun checkColour(): Colours {
        var red = colourSensor.red()
        var green = colourSensor.green()
        var blue = colourSensor.blue()

            var redInTol = abs(red - CS_RED_RGB[0]) <= CS_RED_TOLERANCE[0]
            var greenInTol = abs(green - CS_RED_RGB[1]) <= CS_RED_TOLERANCE[1]
            var blueInTol = abs(blue - CS_RED_RGB[2]) <= CS_RED_TOLERANCE[2]
            if (redInTol && greenInTol && blueInTol) return Colours.RED

            redInTol = abs(red - CS_BLUE_RGB[0]) <= CS_BLUE_TOLERANCE[0]
            greenInTol = abs(green - CS_BLUE_RGB[1]) <= CS_BLUE_TOLERANCE[1]
            blueInTol = abs(blue - CS_BLUE_RGB[2]) <= CS_BLUE_TOLERANCE[2]
            if (redInTol && greenInTol && blueInTol) return Colours.BLUE

            redInTol = abs(red - CS_YELLOW_RGB[0]) <= CS_YELLOW_TOLERANCE[0]
            greenInTol = abs(green - CS_YELLOW_RGB[1]) <= CS_YELLOW_TOLERANCE[1]
            blueInTol = abs(blue - CS_YELLOW_RGB[2]) <= CS_YELLOW_TOLERANCE[2]
            if (redInTol && greenInTol && blueInTol) return Colours.YELLOW

        return Colours.NONE
    }

    enum class Colours {
        RED,
        BLUE,
        YELLOW,
        NONE
    }
}