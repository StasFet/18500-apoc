package org.firstinspires.ftc.teamcode.commands

import com.arcrobotics.ftclib.command.CommandBase
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.subsystems.Lift

class SpecLiftWall(val lift: Lift) : CommandBase() {
    init {
        addRequirements(lift)
    }

    override fun initialize() {
        lift.doIStopPID = false
        lift.pidf.p = 6.0
        lift.setModes(DcMotor.RunMode.RUN_USING_ENCODER)
        lift.specWallSetPoint()
    }

    override fun isFinished(): Boolean {
        return lift.atSetPoint()
    }

    override fun end(interLiftUprupted: Boolean) {
        lift.doIStopBrake = false
    }
}