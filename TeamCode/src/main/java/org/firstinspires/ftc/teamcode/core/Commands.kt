package org.firstinspires.ftc.teamcode.core

import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.qualcomm.hardware.ams.AMSColorSensor.Wait
import org.firstinspires.ftc.teamcode.commands.IntakeExtend
import org.firstinspires.ftc.teamcode.commands.IntakeRetract
import org.firstinspires.ftc.teamcode.commands.IntakeWaitForSample
import org.firstinspires.ftc.teamcode.commands.LiftDown
import org.firstinspires.ftc.teamcode.commands.LiftUp
import org.firstinspires.ftc.teamcode.commands.PrepForTransfer
import org.firstinspires.ftc.teamcode.commands.ZeroIntakeSlides
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.subsystems.*

class Commands(val intake: Intake, val outtake: Outtake, val lift: Lift) {
    fun retractIntake(intake: Intake): CommandBase {
        // if extended, do stuff
        return ConditionalCommand(
                SequentialCommandGroup(
                    InstantCommand({intake.state = SubsystemStates.IntakeStates.EXTENDED}),
                    IntakeWaitForSample(intake),
                    InstantCommand({intake.intakeEject()}),
                    WaitCommand(75 ),
                    InstantCommand({
                        intake.intakeOff()
                        intake.cycleCount++
                    }),
                    IntakeRetract(intake, outtake)
                ),
                //intake.state = SubsystemStates.IntakeStates.EXTENDED,
                // otherwise do nothing!!
                InstantCommand(),
            { intake.state == SubsystemStates.IntakeStates.EXTENDING || intake.state == SubsystemStates.IntakeStates.EXTENDED}
        )
    }

    fun transfer(): Command {
        return ConditionalCommand(
            SequentialCommandGroup(
                PrepForTransfer(intake, outtake),
                InstantCommand({
                    intake.intakeCustomPower(-0.8)
                    intake.brake()
                }),
                WaitCommand(150),
                InstantCommand({outtake.claw.setPosition(0.25)}),
                WaitCommand(150),
                InstantCommand({outtake.setLinkagePos(ARM_LINK_IN)}),
                WaitCommand(200),
                InstantCommand({intake.intakeOff()})
            ),
            WaitCommand(1),
            { intake.state == SubsystemStates.IntakeStates.TRANSFER }
        )


    }

    fun transferFailsafe() : Command {
        return ConditionalCommand(
            WaitCommand(1),
            transfer(),
            {intake.checkColour() == Intake.Colours.NONE}
        )
    }

    fun prepForBasket() : Command {
        return ConditionalCommand(
            ParallelCommandGroup(
                SequentialCommandGroup(
                    InstantCommand({outtake.setPosition(ARM_DEPOSIT_SAMPLE)}),
                    WaitCommand(300),
                    InstantCommand({outtake.setLinkagePos(ARM_LINK_OUT)})
                ),
                LiftUp(lift),
                zeroIntakeSlidesAutomatically(),
                InstantCommand({outtake.clawClose()})
                ),InstantCommand(),
                { intake.state == SubsystemStates.IntakeStates.TRANSFER }
            )
    }

    fun depositAndReturn() : Command {
        return SequentialCommandGroup(
            InstantCommand({outtake.clawOpen()}),
            WaitCommand(150),
            ParallelCommandGroup(
                InstantCommand({outtake.setLinkagePos(ARM_LINK_TRANSFER)}),
                WaitCommand(50),
                InstantCommand({outtake.setPosition(ARM_TRANSFER)}),
                LiftDown(lift, outtake),
                //outtake.state = SubsystemStates.OuttakeState.IDLE
                //dont know why this doesnt work
                //issue: robot cant transfer after B
        ))


    }
    fun specDropOff() : Command {
        return SequentialCommandGroup(
            InstantCommand({outtake.setPosition(ARM_DEPOSIT_SAMPLE)}),
            InstantCommand({outtake.setLinkagePos(ARM_LINK_OUT)}),
        )

    }

    fun specPickUp() : Command {
        return SequentialCommandGroup(
            InstantCommand({outtake.setPosition(ARM_SPEC_WALL)}),
            InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_WALL)})
        )
    }

    fun specHighBar() : Command {
        return SequentialCommandGroup (
            InstantCommand({outtake.setPosition(ARM_SPEC_BAR)}),
            InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_BAR)}),
            InstantCommand({ VSLIDES_SPECIMEN_DEPOSIT})
        )
    }

    fun zeroIntakeSlidesAutomatically() : Command {
        return ConditionalCommand(
            ZeroIntakeSlides(intake),
            WaitCommand(1),
            {intake.cycleCount >= CYCLES_PER_SLIDE_RESET}
        )
    }
}