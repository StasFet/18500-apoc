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
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.subsystems.*

class Commands(val intake: Intake, val outtake: Outtake, val lift: Lift) {
    fun retractIntake(intake: Intake): CommandBase {
        return SequentialCommandGroup(
            IntakeWaitForSample(intake),
            IntakeRetract(intake)
        )
    }

    fun transfer(): Command {
        return SequentialCommandGroup(
            PrepForTransfer(intake, outtake),
            InstantCommand({
                intake.intakeCustomPower(-0.8)
                intake.brake()
            }),
            WaitCommand(50),
            InstantCommand({outtake.clawClose()}),
            WaitCommand(400),
            InstantCommand({intake.intakeOff()})
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
        return ParallelCommandGroup(
            InstantCommand({outtake.setPosition(0.23)}),
            LiftUp(lift)
        )
    }

    fun depositAndReturn() : Command {
        return SequentialCommandGroup(
            InstantCommand({outtake.clawOpen()}),
            WaitCommand(150),
            ParallelCommandGroup(
                InstantCommand({outtake.setPosition(ARM_TRANSFER)}),
                LiftDown(lift)
        ))


    }
}