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
import com.qualcomm.robotcore.hardware.DcMotorImplEx
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.hardware.ServoImplEx
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.PIDTune
import kotlin.math.abs
import kotlin.math.max

@TeleOp(name = "Testing")
class TestTeleop() : LinearOpMode() {

    override fun runOpMode() {

        val gp1: GamepadEx = GamepadEx(gamepad1)

        val slideR = hardwareMap.get(DcMotorEx::class.java, NAME_VSLIDER)
        val slideL = hardwareMap.get(DcMotorEx::class.java, NAME_VSLIDEL)

        slideR.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        slideL.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        slideR.mode = DcMotor.RunMode.RUN_USING_ENCODER
        slideL.mode = DcMotor.RunMode.RUN_USING_ENCODER
        slideR.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        slideL.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        val intake: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, NAME_INTAKE)
        val intakeSlide: DcMotorImplEx = hardwareMap.get(DcMotorImplEx::class.java, NAME_INTSLIDE)

        val intakeLeft: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, NAME_INTAKELEFT)
        val intakeRight: ServoImplEx = hardwareMap.get(ServoImplEx::class.java, NAME_INTAKERIGHT)

        val dashboard: FtcDashboard = FtcDashboard.getInstance()
        waitForStart()

        if (isStopRequested) return


        while (opModeIsActive()) {
            // -930

            if (gamepad1.a) {
                slideR.targetPosition = -930
                slideR.mode = DcMotor.RunMode.RUN_TO_POSITION
                slideR.power = 0.6
            } else if (gamepad1.b) {
                slideR.targetPosition = 0
                slideR.mode = DcMotor.RunMode.RUN_TO_POSITION
                slideR.power = 0.6
            } else {
                slideR.targetPosition = slideR.currentPosition
            }
            slideL.power = -slideR.power

            telemetry.addData("Slide left", slideL.currentPosition)
            telemetry.addData("Slide right", slideR.currentPosition)
            telemetry.update()
        }
    }
}