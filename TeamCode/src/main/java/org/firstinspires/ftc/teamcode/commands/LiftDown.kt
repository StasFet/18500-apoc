package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.core.SubsystemStates
import org.firstinspires.ftc.teamcode.subsystems.Lift
import org.firstinspires.ftc.teamcode.subsystems.Outtake

class LiftDown(val lift: Lift, val outtake: Outtake) : CommandBase() {
    init {
        addRequirements(lift)
        addRequirements(outtake)
    }

    override fun initialize() {
        lift.doIStopBrake = true
        lift.pidf.p = 4.5
        lift.setModes(DcMotor.RunMode.RUN_USING_ENCODER)
        lift.contractionSetPoint()
    }

//    override fun isFinished(): Boolean {
//        return lift.atSetPoint()
//    }

    override fun isFinished() = lift.touch.isPressed

    override fun end(interrupted: Boolean) {
        lift.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER)
        lift.setPowers(0.0)
        outtake.state = SubsystemStates.OuttakeState.IDLE
        lift.doIStopPID = true
    }
}