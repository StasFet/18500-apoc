package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.pedropathing.follower.Follower
import com.pedropathing.localization.GoBildaPinpointDriver
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.Constants.CLAW_CLOSED
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.*
import pedroPathing.constants.*

@TeleOp(name="APOC TeleOp")
class ApocTele() : CommandOpMode() {

    lateinit var robot: Robot
    lateinit var odo: GoBildaPinpointDriver
    lateinit var follower: Follower
    val startPose: Pose = Pose(0.0, 0.0, Math.toRadians(0.0))
    val dashboard: FtcDashboard = FtcDashboard.getInstance()

    lateinit var dt: MecanumDrive
    lateinit var intake: Intake
    lateinit var drive: Drive

    lateinit var intakeExtendBtn: GamepadButton
    lateinit var intakeRetractBtn: GamepadButton
    lateinit var intakeEjectBtn: GamepadButton
    lateinit var grabSpecBtn: GamepadButton
    lateinit var lowBarBtn: GamepadButton

    override fun initialize() {
        robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)
        robot.setColours()//Intake.Colours.RED, Intake.Colours.BLUE) //choose colours
        odo = robot.odo
        follower = robot.follower
        dt = MecanumDrive(robot)
        intake = Intake(robot)

        drive = Drive(dt, robot)

        intakeExtendBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.A)
        intakeRetractBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.B)
        intakeEjectBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.X)
        grabSpecBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.DPAD_DOWN)
        lowBarBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.DPAD_UP)


        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        register(dt, intake)
        dt.defaultCommand = drive
        robot.intakeSlide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        telemetry.msTransmissionInterval = 25

        intakeExtendBtn.whenPressed(IntakeExtend(intake), true)
        //intakeExtendBtn.whenPressed(InstantCommand({intake.intakeOn()}))
        intakeRetractBtn.whenPressed(SequentialCommandGroup(
            IntakeWaitForSample(intake),
            IntakeRetract(intake)) , true)
        grabSpecBtn.whenPressed(ArmToSpec(robot))
        intakeEjectBtn.whenHeld(IntakeEject(intake))
        lowBarBtn.whenPressed(SequentialCommandGroup(
                InstantCommand({robot.claw.position = CLAW_CLOSED}),
                WaitCommand(250),
                ArmToLowBar(robot)
            )
        )
    }

    override fun run() {
        odo.update()
        follower.update()
        //runtimeControls()
        super.run()
        telemetry.update()

        val packet: TelemetryPacket = TelemetryPacket()
        packet.put("Target", intake.controller.setPoint)
        packet.put("Position", intake.slide.currentPosition)
        packet.put("Power", intake.slide.power)
        dashboard.sendTelemetryPacket(packet)
    }
}