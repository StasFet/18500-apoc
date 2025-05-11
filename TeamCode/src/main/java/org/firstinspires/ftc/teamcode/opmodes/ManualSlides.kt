package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import org.firstinspires.ftc.teamcode.core.Constants.*

@TeleOp(name="Manual Slides")
class ManualSlides: LinearOpMode() {
    override fun runOpMode() {
        val left = hardwareMap.get(CRServo::class.java, NAME_HANGLEFT)
        val right = hardwareMap.get(CRServo::class.java, NAME_HANGRIGHT)
        val gp1 = GamepadEx(gamepad1)

        waitForStart()
        while (opModeIsActive()) {
            left.power = -gp1.leftY
            right.power = gp1.rightY
        }
    }
}