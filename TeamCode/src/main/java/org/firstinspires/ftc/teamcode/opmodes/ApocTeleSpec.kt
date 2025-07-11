package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.TelemetryPacket
import com.arcrobotics.ftclib.command.CommandOpMode
import com.arcrobotics.ftclib.command.CommandScheduler
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
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
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.core.Robot
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.*
import pedroPathing.constants.*

@TeleOp(name="APOC TeleOp Specimen")
class ApocTeleSpec() : CommandOpMode() {

    lateinit var robot: Robot
    lateinit var odo: GoBildaPinpointDriver
    lateinit var follower: Follower
    val startPose: Pose = Pose(0.0, 0.0, Math.toRadians(0.0))
    val dashboard: FtcDashboard = FtcDashboard.getInstance()
    lateinit var CMD: Commands
    var isReadyForPickup = false;

    lateinit var dt: MecanumDrive
    lateinit var intake: Intake
    lateinit var drive: Drive
    lateinit var fcDrive: FcDrive
    lateinit var outtake: Outtake
    lateinit var lift: Lift

    lateinit var intakeExtendBtn: GamepadButton
    lateinit var intakeRetractBtn: GamepadButton
    lateinit var intakeEjectBtn: GamepadButton
    lateinit var transferBtn: GamepadButton
    lateinit var liftUpBtn: GamepadButton
    lateinit var liftDownBtn: GamepadButton
    lateinit var hSlideResetBtn: GamepadButton
    lateinit var vSlideResetBtn: GamepadButton
    lateinit var stopEverythingBtn: GamepadButton
    lateinit var intakeStartPositionBtn: GamepadButton
    lateinit var specDropOffBtn: GamepadButton
    lateinit var pickUpSpecBtn: GamepadButton
    lateinit var grabFromWallBtn: GamepadButton

    override fun initialize() {

        CommandScheduler.getInstance().reset()
        robot = Robot(hardwareMap, telemetry, gamepad1, gamepad2)
        robot.setColours(Intake.Colours.BLUE, Intake.Colours.YELLOW)    //Intake.Colours.RED, Intake.Colours.BLUE) //choose colours
        odo = robot.odo
        follower = robot.follower
        dt = MecanumDrive(robot)
        intake = Intake(robot)
        outtake = Outtake(robot)
        lift = Lift(robot)

        CMD = Commands(intake, outtake, lift)

        drive = Drive(dt, robot)
        fcDrive = FcDrive(dt)

        intakeExtendBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.Y) //was originally B
        intakeRetractBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.B) //was originally AZ
        intakeEjectBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.X)
        transferBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.A)
        specDropOffBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.LEFT_BUMPER)
        pickUpSpecBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.RIGHT_BUMPER)
        grabFromWallBtn = GamepadButton(robot.gpGeneral, GamepadKeys.Button.DPAD_UP)


        hSlideResetBtn = GamepadButton(robot.gpDrive, GamepadKeys.Button.DPAD_LEFT)
        vSlideResetBtn = GamepadButton(robot.gpDrive, GamepadKeys.Button.DPAD_UP)
        stopEverythingBtn = GamepadButton(robot.gpDrive, GamepadKeys.Button.X) //2nd driver aka stas
        intakeStartPositionBtn = GamepadButton(robot.gpDrive, GamepadKeys.Button.Y) //2nd driver aka stas

        //Constants.setConstants(FConstants::class.java, LConstants::class.java)
        register(dt, intake, outtake)
        dt.defaultCommand = drive
        robot.intakeSlide.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        telemetry.msTransmissionInterval = 25

        CommandScheduler.getInstance().schedule(SequentialCommandGroup(
            InstantCommand({
                outtake.clawOpen()
                outtake.setPosition(0.6)
                outtake.setLinkagePos(ARM_LINK_IN)

                //servos in intake
                intake.wristToPos(INTAKE_HOVER)
                intake.closeIntakeStopper()
            }),
            WaitCommand(500),
            ParallelCommandGroup(
                ZeroVerticalSlides(lift), ZeroIntakeSlides(intake)
            )
        ))

        CommandScheduler.getInstance().schedule(AlwaysUpdateLiftPID(lift))

        intakeExtendBtn.whenPressed(IntakeExtend(intake), true)
        intakeRetractBtn.whenPressed(SequentialCommandGroup(
            InstantCommand({outtake.setPosition(ARM_TRANSFER)}),
            CMD.retractIntake(intake),
            WaitCommand(50),
            CMD.transfer(),
            //CMD.transferFailsafe(),
            WaitCommand(200),
            CMD.prepForSpec(),
            ParallelCommandGroup(
                CMD.zeroIntakeSlidesAutomatically(),
                InstantCommand({outtake.clawClose()})
            )), true)

        intakeEjectBtn.whenHeld(
            IntakeEject(intake)
        )

        //transferBtn.whenPressed(CMD.transfer(intake, outtake), true)

        hSlideResetBtn.whenPressed(ZeroIntakeSlides(intake))

        intakeStartPositionBtn.whenPressed(SequentialCommandGroup(
            InstantCommand({intake.wristToPos(INTAKE_START_POSITION)}),
            InstantCommand({intake.state = SubsystemStates.IntakeStates.IDLE}),
        ))

        specDropOffBtn.whenPressed(SequentialCommandGroup(
            CMD.specDropOff(),
            CMD.prepForSpec()
        ))

        pickUpSpecBtn.whenPressed(
            SequentialCommandGroup(
                //LiftDown(lift, outtake),
                InstantCommand({outtake.setLinkagePos(ARM_LINK_IN)}),
                WaitCommand(50),
                InstantCommand  ({outtake.setPosition(ARM_SPEC_WALL)}),
                WaitCommand(50),
                InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_WALL)}),
                InstantCommand({isReadyForPickup = true})
            )
        )

        grabFromWallBtn.whenPressed(
            SequentialCommandGroup(
                InstantCommand({isReadyForPickup = false}),
                InstantCommand({outtake.claw.setPosition(0.25)}),
                WaitCommand(200),
                CMD.specHighBar(),
                InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_BAR)}),
            )
        )



//        pickUpSpecBtn.whenPressed(ConditionalCommand(
//            // onTrue
//            SequentialCommandGroup(
//                InstantCommand({isReadyForPickup = false}),
//                InstantCommand({outtake.claw.setPosition(0.25)}),
//                WaitCommand(50),
//                CMD.specHighBar()
//            ),
//            // onFalse
//            SequentialCommandGroup(
//                LiftDown(lift, outtake),
//                InstantCommand({outtake.setLinkagePos(ARM_LINK_IN)}),
//                WaitCommand(50),
//                InstantCommand  ({outtake.setPosition(ARM_SPEC_WALL)}),
//                WaitCommand(50),
//                InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_WALL)}),
//                WaitCommand(1000),
//                InstantCommand({isReadyForPickup = true})
//            ),
//            {isReadyForPickup}
//        ))
    }

    override fun run() {
        odo.update()
        follower.update()
        runtimeControls()
        super.run()
        telemetry()
    }

    fun runtimeControls() {

    }

    fun telemetry() {
        val packet = TelemetryPacket()
        packet.put("Target", intake.controller.setPoint)
        packet.put("Position", intake.slide.currentPosition)
        packet.put("Vert pos", lift.right.currentPosition)
        packet.put("Vert setpoint", lift.pidf.setPoint)
        packet.put("Vert L target", lift.left.targetPosition)
        packet.put("Vert R target", lift.right.targetPosition)
        packet.put("Intake Current", intake.intake.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Intake Slide Current", intake.slide.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Left lift current", lift.left.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Right lift current", lift.right.getCurrent(CurrentUnit.MILLIAMPS))
        packet.put("Claw position", outtake.claw.position)
        packet.put("doIStopPID", lift.doIStopPID)
        packet.put("doIStopBrake", lift.doIStopBrake)
        dashboard.sendTelemetryPacket(packet)

        //telemetry.addData("")
        //telemetry.addData("Intake Current", intake.intake.getCurrent(CurrentUnit.MILLIAMPS))
        //telemetry.addData("Colour detected", intake.latestColour)
        telemetry.addData("Intake position", intake.slide.currentPosition)
        telemetry.addData("Intake slide power", intake.slide.power)
        telemetry.addData("Left vslide power", lift.left.power)
        telemetry.addData("Right vslide power", lift.right.power)
        telemetry.addData("Intake power", intake.intake.power)
        telemetry.addData("doIStopPID", lift.doIStopPID)
        telemetry.addData("doIStopBrake", lift.doIStopBrake)
        telemetry.update()
    }
}