package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.command.CommandOpMode
import com.pedropathing.follower.Follower
import com.pedropathing.localization.GoBildaPinpointDriver
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.subsystems.*
import pedroPathing.constants.*

@TeleOp(name="APOC Teleop")
class ApocTele() : CommandOpMode() {

    lateinit var robot: Robot
    lateinit var odo: GoBildaPinpointDriver
    lateinit var follower: Follower
    val startPose: Pose = Pose(0.0, 0.0, Math.toRadians(0.0))

    lateinit var dt: MecanumDrive
    lateinit var intake: Intake
    lateinit var drive: Drive

    override fun initialize() {
        robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)
        odo = robot.odo
        follower = robot.follower
        dt = MecanumDrive(robot)
        //intake = Intake(robot)
        drive = Drive(dt, robot)

        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        register( dt);
        dt.defaultCommand = drive;
    }

    override fun run() {
        odo.update()
        follower.update()
        runtimeControls()
        super.run()
        telemetry.update()
    }

    private fun runtimeControls() {
//        when (intake.state) {
//            SubsystemStates.IntakeStates.IDLE -> {
//                //if button pressed, schedule IntakeExtendAndReady
//            }
//            SubsystemStates.IntakeStates.EXTENDED_AND_DOWN -> {
//                //if button pressed, schedule IntakeDetectAndRetract
//            }
//            SubsystemStates.IntakeStates.TRANSFER -> {
//                //if button pressed, schedule TransferAndReady
//            }
//            else -> {}
//        }
    }
}