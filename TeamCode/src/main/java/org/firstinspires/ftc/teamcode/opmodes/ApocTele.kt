package org.firstinspires.ftc.teamcode.opmodes

import com.arcrobotics.ftclib.command.CommandOpMode
import com.pedropathing.follower.Follower
import com.pedropathing.localization.GoBildaPinpointDriver
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.state.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.*
import pedroPathing.constants.*

class ApocTele() : CommandOpMode() {

    val robot: Robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)
    val odo: GoBildaPinpointDriver = robot.odo
    val follower: Follower = robot.follower
    val startPose: Pose = Pose(0.0, 0.0, Math.toRadians(0.0))

    val dt: MecanumDrive by lazy { MecanumDrive(robot) }
    val intake: Intake by lazy { Intake(robot) }

    val drive: Drive by lazy { Drive(dt, robot) }

    override fun initialize() {
        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        register(intake, dt);
        dt.setDefaultCommand(drive);
    }

    override fun run() {
        odo.update()
        follower.update()
        runtimeControls()
        super.run()
    }

    private fun runtimeControls() {
        when (intake.state) {
            SubsystemStates.IntakeStates.IDLE -> {
                //if button pressed, schedule IntakeExtendAndReady
            }
            SubsystemStates.IntakeStates.EXTENDED -> {
                //if button pressed, schedule IntakeDetectAndRetract
            }
            SubsystemStates.IntakeStates.TRANSFER -> {
                //if button pressed, schedule TransferAndReady
            }
            else -> {}
        }
    }
}