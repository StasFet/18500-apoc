package org.firstinspires.ftc.teamcode.subsystems

import com.acmerobotics.dashboard.config.Config
import com.arcrobotics.ftclib.command.SubsystemBase
import com.arcrobotics.ftclib.controller.PIDFController
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode._deprecated.Intake.IntakeColour
import org.firstinspires.ftc.teamcode.core.*
import org.firstinspires.ftc.teamcode.core.Constants.*
import org.firstinspires.ftc.teamcode.state.SubsystemStates

@Config
class Intake(val robot: Robot) : SubsystemBase() {
    val intake: DcMotorEx = robot.intakeSpin
    val slide: DcMotorEx = robot.intakeSlide
    val colourSensor: RevColorSensorV3 = robot.intakeColorSensor
    val timer: ElapsedTime = ElapsedTime()

    var state = SubsystemStates.IntakeStates.IDLE
    var intakeColour: IntakeColour = robot.intakeColor
    var intakeBusy = false

    val Kp = 1.0
    val Ki = 0.0
    val Kd = 0.1;
    val Kf = 0.0001;

    val pidf: PIDFController = PIDFController(Kp, Ki, Kd, Kf)

    init {
        pidf.setTolerance(INTAKE_TOLERANCE)
    }

    // inaccurate name but idk what else to call it
    public fun slidesAtTarget(): Boolean {
        slide.velocity = pidf.calculate(slide.currentPosition.toDouble())
        return pidf.atSetPoint()
    }

    enum class IntakeColour {
        RED,
        BLUE,
        REDYELLOW,
        BLUEYELLOW,
        YELLOW
    }

    enum class Colours {
        RED,
        BLUE,
        YELLOW
    }
}