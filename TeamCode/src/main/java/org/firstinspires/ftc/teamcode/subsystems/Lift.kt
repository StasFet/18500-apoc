package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot

@Config
class Lift(val robot: Robot) : SubsystemBase() {
    val motor1 = robot.vslide1
    val motor2 = robot.vslide2

    val p = 1.0
    val i = 0.0
    val d = 0.1
    val f = 0.0001

    val pidf: PIDFController = PIDFController(p, i, d, f)

    init {
        motor1.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor2.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        motor1.mode = DcMotor.RunMode.RUN_USING_ENCODER
        motor2.mode = DcMotor.RunMode.RUN_USING_ENCODER

        pidf.setTolerance(LIFT_TOLERANCE)
    }
}