package org.firstinspires.ftc.teamcode.core

import android.widget.GridLayout.Spec
import com.arcrobotics.ftclib.command.Command
import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.ConditionalCommand
import com.arcrobotics.ftclib.command.InstantCommand
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.ParallelDeadlineGroup
import com.arcrobotics.ftclib.command.ParallelRaceGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import com.qualcomm.hardware.ams.AMSColorSensor.Wait
import org.firstinspires.ftc.teamcode.commands.IntakeExtend
import org.firstinspires.ftc.teamcode.commands.*
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.subsystems.*

class Commands(val intake: Intake, val outtake: Outtake, val lift: Lift) {
    fun retractIntake(intake: Intake): CommandBase {
        // if extended, do stuff
        return ConditionalCommand(
                SequentialCommandGroup(
                    InstantCommand({intake.state = SubsystemStates.IntakeStates.EXTENDED}),
                    ParallelDeadlineGroup(
                        IntakeWaitForSample(intake),
                        LiftDown(lift, outtake),
                    ),
                    InstantCommand({intake.intakeCustomPower(1.0)}),
                    WaitCommand(20),
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
                WaitCommand(75),
                InstantCommand({intake.intakeOff()}),
                InstantCommand({outtake.claw.setPosition(0.25)}),
                WaitCommand(150),
                InstantCommand({outtake.setLinkagePos(ARM_LINK_IN)}),

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
                    WaitCommand(100),
                    InstantCommand({outtake.setPosition(ARM_DEPOSIT_SAMPLE)}),
                    WaitCommand(300),
                    InstantCommand({outtake.setLinkagePos(ARM_LINK_OUT)})
                ),
                LiftUp(lift)
                ),InstantCommand(),
                { intake.state == SubsystemStates.IntakeStates.TRANSFER }
            )
    }

    // goes to the wall position
    fun prepForSpec() : Command {
        return ConditionalCommand(
            SequentialCommandGroup(
                InstantCommand({outtake.clawOpen()}),
                SpecLiftWall(lift),
                WaitCommand(50),
                InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_WALL)}),

                WaitCommand(200),
                InstantCommand({outtake.setPosition(ARM_SPEC_WALL)}),

            ),
            InstantCommand(),
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
            InstantCommand({outtake.clawOpen()}),
            WaitCommand(100),
            InstantCommand({outtake.setLinkagePos(ARM_LINK_TRANSFER)}),
            WaitCommand(50),
            InstantCommand({outtake.setPosition(ARM_TRANSFER)}),
            WaitCommand(50),
            LiftDown(lift,outtake)

            )

    }

    /*fun specPickUp() : Command {
        return SequentialCommandGroup(
            WaitCommand(50),
            InstantCommand({outtake.setPosition(ARM_SPEC_WALL)}),
            WaitCommand(50),
            InstantCommand({outtake.setLinkagePos(LINKAGE_SPEC_WALL)}),


        )
    }*/

    fun specHighBar() : Command {
        return ParallelCommandGroup(
            SequentialCommandGroup(
                InstantCommand({outtake.setPosition(ARM_SPEC_BAR)}),
                WaitCommand(100),
                InstantCommand({outtake.clawClose()}),
                WaitCommand(500),
                InstantCommand({outtake.setLinkagePos(ARM_LINK_OUT)}),


            ),
            SpecLiftBar(lift)
        )
    }

    fun zeroIntakeSlidesAutomatically() : Command {
        return ConditionalCommand(
            SequentialCommandGroup(
                ZeroIntakeSlides(intake),
                InstantCommand({intake.cycleCount = 0})
            ),
            InstantCommand(),
            {intake.cycleCount >= CYCLES_PER_SLIDE_RESET}
        )
    }

    // to run after scoring a specimen in auto to reset for grabbing spec from wall
    fun postSpecAuto() : Command {
        return SequentialCommandGroup(
            InstantCommand({outtake.clawOpen()}),
            WaitCommand(50),
            InstantCommand({
                outtake.setLinkagePos(LINKAGE_SPEC_WALL)
                outtake.setPosition(ARM_SPEC_WALL)
            }),
            LiftDown(lift, outtake)
        )
    }
}