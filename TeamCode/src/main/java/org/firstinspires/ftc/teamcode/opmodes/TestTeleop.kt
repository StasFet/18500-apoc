package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.hardware.motors.CRServo
import com.qualcomm.robotcore.eventloop.opmode.*
import org.firstinspires.ftc.teamcode.core.Robot

@Config
@TeleOp(name = "Testing")
class TestTeleop() : LinearOpMode() {
    // tunable values
    public val hangPower: Double = 0.5
    public val hangLeftPower: Double = 0.0
    public val hangRightPower: Double = 0.0

    val robot: Robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)

    val hangLeft: CRServo = robot.hangLeft
    val hangRight: CRServo  = robot.hangRight

    override fun runOpMode() {
        waitForStart()
        while (opModeIsActive()) {
            hangLeft.set(hangLeftPower)
            hangRight.set(hangRightPower)
        }
    }
}