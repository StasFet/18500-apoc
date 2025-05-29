package org.firstinspires.ftc.teamcode.core

import com.arcrobotics.ftclib.command.CommandBase
import com.arcrobotics.ftclib.command.ParallelCommandGroup
import com.arcrobotics.ftclib.command.SequentialCommandGroup
import com.arcrobotics.ftclib.command.WaitCommand
import org.firstinspires.ftc.teamcode.commands.IntakeExtend
import org.firstinspires.ftc.teamcode.commands.IntakeRetract
import org.firstinspires.ftc.teamcode.commands.IntakeWaitForSample
import org.firstinspires.ftc.teamcode.subsystems.Intake

class Commands {
    public fun extendIntake(intake: Intake): CommandBase {
        return SequentialCommandGroup(
            IntakeWaitForSample(intake),
            IntakeRetract(intake)
        )
    }
}