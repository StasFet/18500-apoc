package org.firstinspires.ftc.teamcode.subsystems

import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.core.Constants.FRONT_DT_MULTI
import org.firstinspires.ftc.teamcode.core.Constants.REAR_DT_MULTI
import org.firstinspires.ftc.teamcode.core.Robot
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sign

class MecanumDrive(val robot: Robot) : SubsystemBase() {

    private val fl = robot.fl
    private val fr = robot.fr
    private val bl = robot.bl
    private val br = robot.br
    private val gp: GamepadEx = robot.gpGeneral
    private val odo = robot.odo

    init {
        fr.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        fl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        br.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        bl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        fl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        fr.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        bl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        br.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER

        fr.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        fl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        br.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        bl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }


    public fun rcDrive() {
        val y = gp.leftY
        val x = gp.leftX * 1.1 // Counteract imperfect strafing
        var rx = gp.rightX

        if (abs(rx) < 0.05){
            rx = 0.0
        } else {
            rx = sign(gp.rightX) * abs(gp.rightX).pow(3.0) * 0.6;
        }

        val denominator = max(abs(y) + abs(x) + abs(rx), 1.0)
        val frontLeftPower = (y + x + rx) / denominator
        val backLeftPower = (y - x + rx) / denominator
        val frontRightPower = (y - x - rx) / denominator
        val backRightPower = (y + x - rx) / denominator

        fl.power = frontLeftPower * FRONT_DT_MULTI
        bl.power = backLeftPower * REAR_DT_MULTI
        fr.power = frontRightPower * FRONT_DT_MULTI
        br.power = backRightPower * REAR_DT_MULTI
    }
}