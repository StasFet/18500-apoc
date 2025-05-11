package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.eventloop.opmode.*
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.core.Constants.*
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "Testing")
class TestTeleop() : LinearOpMode() {

    override fun runOpMode() {

        val hangLeft: CRServo = hardwareMap.get(CRServo::class.java, NAME_HANGLEFT)
        val hangRight: CRServo = hardwareMap.get(CRServo::class.java, NAME_HANGRIGHT)
        val encoderLeft: AnalogInput = hardwareMap.get(AnalogInput::class.java, NAME_HANGLEFTENC)
        val gp1: GamepadEx = GamepadEx(gamepad1)

        val fr: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_FR)
        val fl: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_FL)
        val br: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_BR)
        val bl: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_BL)

        var revolutions = 0
        var lastPos = 0.0

        var statelol = state.IDLE
        waitForStart()
        while (opModeIsActive()) {
            //hangLeft.power = -gp1.leftY
            //hangRight.power = gp1.rightY
            //caca radit poo
            when (statelol) {
                state.IDLE -> {
                    if (gamepad1.a) statelol = state.ASCENDING
                }

                state.ASCENDING -> {
                    if (revolutions < 9) {
                        hangLeft.power = -1.0
                        hangRight.power = -1.0
                    } else statelol = state.UP
                }

                state.UP -> {
                    if (gamepad1.b) statelol = state.DESCENDING
                    hangLeft.power = 0.0
                    hangRight.power = 0.0
                }

                state.DESCENDING -> {
                    if (revolutions > 5) {
                        hangLeft.power = 1.0
                        hangRight.power = 1.0
                    } else {
                        statelol = state.DONE
                    }
                }

                else -> {}
            }

            val pos: Double = encoderLeft.voltage / 3.3 * 360
            if (lastPos > 230 && pos < 100) revolutions++
            if (lastPos < 100 && pos > 230) revolutions--

            telemetry.addData("Encoder: ", pos)
            telemetry.addData("Revolutions:", revolutions)
            //telemetry.addData("Revolutions: ", encoderLeft.)
            telemetry.update()
            lastPos = pos
        }
    }

    enum class state {
        IDLE, ASCENDING, UP, DESCENDING, DONE
    }
}