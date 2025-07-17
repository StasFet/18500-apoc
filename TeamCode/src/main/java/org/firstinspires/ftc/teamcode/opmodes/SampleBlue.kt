package org.firstinspires.ftc.teamcode.opmodes

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.subsystems.Intake

@TeleOp
class SampleBlue(): ApocTeleBase() {
    override fun initialize() {
        super.initRobot()
        super.robot.setColours(Intake.Colours.BLUE, Intake.Colours.YELLOW)
        super.initialize()
    }
}