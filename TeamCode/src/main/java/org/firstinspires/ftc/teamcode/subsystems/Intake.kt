package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.arcrobotics.ftclib.hardware.ServoEx
import com.acmerobotics.dashboard.FtcDashboard
import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.controller.PIDController
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.commands.LiftDown
import org.firstinspires.ftc.teamcode.core.*
import org.firstinspires.ftc.teamcode.core.Constants.*
import kotlin.math.abs

@Config
public class Intake(val robot: Robot) : SubsystemBase() {

    var p = PIDTune.P //P is the amount you want to overshoot so the system reacts accordingly and in good amount of time
    var i = PIDTune.I //look at how much error is accumulating overtime and responding based on that, basically small errors in steady state that the P wont fix, amplifys small errors that are being built up over time
    var d = PIDTune.D //predicting future values? look at how its changing over time //be careful

    val intake: DcMotorEx = robot.intakeSpin
    val slide: DcMotorEx = robot.intakeSlide
    val leftServo: Servo = robot.intakeLeft
    val rightServo: Servo = robot.intakeRight
    val colourSensor: RevColorSensorV3 = robot.intakeColorSensor
    val intakeStopper: Servo = robot.intakeStopper
    val touch = robot.hSlideTouch
    val timer: ElapsedTime = ElapsedTime()
    val cols = robot.cols
    var isEjecting = false
    var cachedPower = 0.0
    var latestColour = "NONE"
    var cycleCount = 0

    val dashboard: FtcDashboard = FtcDashboard.getInstance()
    var ejectWaiting = false
    var state = SubsystemStates.IntakeStates.IDLE
    var intakeBusy = false

    val controller: PIDController = PIDController(p, i, d)

    init {
        colourSensor.initialize()
        //slide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
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

    fun contractionSetPoint() { customSetPoint(INTAKE_IN_POS) }
    fun extensionSetPoint() { customSetPoint(INTAKE_OUT_POS) }
    fun customSetPoint(setPoint: Double) {
        //slide.mode = DcMotor.RunMode.RUN_USING_ENCODER
         controller.setPoint = setPoint
    }

    fun runToPos(pos: Double, power: Double) {
        slide.targetPosition = pos.toInt()
        slide.mode = DcMotor.RunMode.RUN_TO_POSITION
        slide.power = power
    }

    fun intakeOn() { intakeCustomPower(INTAKE_PWR) }
    fun intakeOff() { intakeCustomPower(0.0) }
    fun intakeEject() {
        intakeCustomPower(INTAKE_EJECT_PWR)
    }
    fun intakeCustomPower(power: Double) {
        if (cachedPower != power) {
            intake.power = power
            cachedPower = power
        }
    }

    fun openIntakeStopper() {
        intakeStopper.position = INTAKE_STOP_OPEN
    }

    fun closeIntakeStopper() {
        intakeStopper.position = INTAKE_STOP_CLOSED
    }

    fun wristToPos(pos: Double) {
        leftServo.position = 1 - pos
        rightServo.position = pos
    }

    fun brake() {
        runToPos(slide.currentPosition.toDouble(), 0.4)
    }

    fun checkColour(): Colours {
        var red = colourSensor.red()
        var green = colourSensor.green()
        var blue = colourSensor.blue()

        var redInTol = abs(red - CS_YELLOW_RGB[0]) <= CS_YELLOW_TOLERANCE[0]
        var greenInTol = abs(green - CS_YELLOW_RGB[1]) <= CS_YELLOW_TOLERANCE[1]
        var blueInTol = abs(blue - CS_YELLOW_RGB[2]) <= CS_YELLOW_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "YELLOW"
            return Colours.YELLOW
        }

        redInTol = abs(red - CS_RED_RGB[0]) <= CS_RED_TOLERANCE[0]
        greenInTol = abs(green - CS_RED_RGB[1]) <= CS_RED_TOLERANCE[1]
        blueInTol = abs(blue - CS_RED_RGB[2]) <= CS_RED_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "RED"
            return Colours.RED
        }

        redInTol = abs(red - CS_BLUE_RGB[0]) <= CS_BLUE_TOLERANCE[0]
        greenInTol = abs(green - CS_BLUE_RGB[1]) <= CS_BLUE_TOLERANCE[1]
        blueInTol = abs(blue - CS_BLUE_RGB[2]) <= CS_BLUE_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "BLUE"
            return Colours.BLUE
        }

        latestColour = "NONE"
        return Colours.NONE
    }

    fun isStalling(): Boolean {
        return intake.getCurrent(CurrentUnit.MILLIAMPS) >= INTAKE_STALL_CURRENT_MA
    }

    enum class Colours {
        RED,
        BLUE,
        YELLOW,
        NONE
    }
}