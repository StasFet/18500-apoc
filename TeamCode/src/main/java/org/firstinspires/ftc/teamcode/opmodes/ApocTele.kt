package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.arcrobotics.ftclib.command.button.GamepadButton
import com.arcrobotics.ftclib.gamepad.GamepadKeys
import com.pedropathing.follower.Follower
import com.pedropathing.localization.GoBildaPinpointDriver
import com.pedropathing.localization.Pose
import com.pedropathing.util.Constants
import com.qualcomm.hardware.ams.AMSColorSensor.Wait
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.Commands
import org.firstinspires.ftc.teamcode.core.Constants.ARM_TRANSFER
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
    lateinit var CMD: Commands

    lateinit var dt: MecanumDrive
    lateinit var intake: Intake
    lateinit var drive: Drive
    lateinit var outtake: Outtake
    lateinit var lift: Lift

    lateinit var intakeExtendBtn: GamepadButton
    lateinit var intakeRetractBtn: GamepadButton
    lateinit var intakeEjectBtn: GamepadButton
    lateinit var transferBtn: GamepadButton
    lateinit var liftUpBtn: GamepadButton
    lateinit var liftDownBtn: GamepadButton

    override fun initialize() {
        CommandScheduler.getInstance().reset()
        robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)
        robot.setColours(Intake.Colours.BLUE, Intake.Colours.YELLOW)//Intake.Colours.RED, Intake.Colours.BLUE) //choose colours
        odo = robot.odo
        follower = robot.follower
        dt = MecanumDrive(robot)
        intake = Intake(robot)
        outtake = Outtake(robot)
        lift = Lift(robot)

        CMD = Commands(intake, outtake, lift)

        drive = Drive(dt, robot)

        intakeExtendBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.A)
        intakeRetractBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.B)
        intakeEjectBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.X)
        transferBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.Y)
        liftUpBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.DPAD_UP)
        liftDownBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.DPAD_DOWN)

        Constants.setConstants(FConstants::class.java, LConstants::class.java)
        register(dt, intake, outtake)
        dt.defaultCommand = drive
        //CommandScheduler.getInstance().schedule(false, ZeroIntakeSlides(intake))
        robot.intakeSlide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        telemetry.msTransmissionInterval = 25

        intakeExtendBtn.whenPressed(IntakeExtend(intake), true)
        intakeRetractBtn.whenPressed(SequentialCommandGroup(
            InstantCommand({outtake.setPosition(ARM_TRANSFER)}),
            CMD.retractIntake(intake),
            WaitCommand(50),
            CMD.transfer(),
            CMD.transferFailsafe(),
            WaitCommand(50),
            CMD.prepForBasket()
            ), false)
        intakeEjectBtn.whenHeld(IntakeEject(intake))
        //transferBtn.whenPressed(CMD.transfer(intake, outtake), true)
        liftUpBtn.whenPressed(LiftUp(lift))
        liftDownBtn.whenPressed(CMD.depositAndReturn())

        outtake.clawOpen()
        outtake.setPosition(0.6)
    }

    override fun run() {
        odo.update()
        follower.update()
        //runtimeControls()
        super.run()
        telemetry()
    }

    fun telemetry() {
        val packet = TelemetryPacket()
        packet.put("Target", intake.controller.setPoint)
        packet.put("Position", intake.slide.currentPosition)
        packet.put("Vert pos", lift.right.currentPosition)
        packet.put("Vert target", lift.pidf.setPoint)
        packet.put("Intake Current", intake.intake.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Intake Slide Current", intake.slide.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Left lift current", lift.left.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Right lift current", lift.right.getCurrent(CurrentUnit.MILLIAMPS))
        dashboard.sendTelemetryPacket(packet)

        //telemetry.addData("")
        //telemetry.addData("Intake Current", intake.intake.getCurrent(CurrentUnit.MILLIAMPS))
        //telemetry.addData("Colour detected", intake.latestColour)
        telemetry.addData("Intake position", intake.slide.currentPosition)
        telemetry.addData("Intake slide power", intake.slide.power)
        telemetry.addData("Intake power", intake.intake.power)
        telemetry.update()
    }
}