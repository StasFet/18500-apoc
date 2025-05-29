package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.ValueProvider
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.arcrobotics.ftclib.hardware.ServoEx
import com.arcrobotics.ftclib.hardware.motors.Motor
import com.qualcomm.robotcore.eventloop.opmode.*
import com.qualcomm.robotcore.hardware.AnalogInput
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.PIDTune
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "Testing")
class TestTeleop() : LinearOpMode() {

    override fun runOpMode() {

        val gp1: GamepadEx = GamepadEx(gamepad1)

        val intake: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_INTAKE)
        val intakeSlide: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_INTSLIDE)

        val intakeLeft: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, NAME_INTAKELEFT)
        val intakeRight: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, NAME_INTAKERIGHT)

        val dashboard: FtcDashboard = FtcDashboard.getInstance()

        intakeSlide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        waitForStart()

        if (isStopRequested) return


        while (opModeIsActive()) {
            dashboard.updateConfig()

            intakeRight.position = PIDTune.intake
            intakeLeft.position = 1 - PIDTune.intake

            if (gamepad1.a) {
                intake.power = -1.0
            } else if (gamepad1.y) {
                intake.power = 1.0
            } else {
                intake.power = 0.0
            }

            if (gamepad1.y) {
                intake.power = 1.0
            }

            telemetry.addData("Intake slide pos: ", intakeSlide.currentPosition)
            telemetry.addData("Intake wrist pos:", intakeRight.position)
            telemetry.addData("dashboard pos:", PIDTune.intake)
            telemetry.update()
        }
    }
}