package org.firstinspires.ftc.teamcode.opmodes

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry
import com.qualcomm.hardware.rev.RevColorSensorV3
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.core.Constants.CS_BLUE_RGB
import org.firstinspires.ftc.teamcode.core.Constants.CS_BLUE_TOLERANCE
import org.firstinspires.ftc.teamcode.core.Constants.CS_RED_RGB
import org.firstinspires.ftc.teamcode.core.Constants.CS_RED_TOLERANCE
import org.firstinspires.ftc.teamcode.core.Constants.CS_YELLOW_RGB
import org.firstinspires.ftc.teamcode.core.Constants.CS_YELLOW_TOLERANCE
import org.firstinspires.ftc.teamcode.core.Constants.NAME_COLOURSENSOR
import org.firstinspires.ftc.teamcode.subsystems.Intake.Colours
import kotlin.math.abs

@TeleOp(name = "Colour Sensor Tune")
class ColourSensorTune : LinearOpMode() {
    lateinit var colourSensor: RevColorSensorV3
    var latestColour = ""
    override fun runOpMode() {
        telemetry = MultipleTelemetry(telemetry, FtcDashboard.getInstance().telemetry)
        colourSensor = hardwareMap.get(RevColorSensorV3::class.java, NAME_COLOURSENSOR)
        colourSensor.initialize()
        waitForStart()
        while (opModeIsActive()) {
            if (!colourSensor.isLightOn) colourSensor.enableLed(true)
            telemetry.addData("red", colourSensor.red())
            telemetry.addData("blue", colourSensor.blue())
            telemetry.addData("green", colourSensor.green())
            checkColour()
            var out = latestColour
            telemetry.addData("Colour Detected", out)
            telemetry.update()
        }
    }

    fun checkColour(): Colours {
        var red = colourSensor.red()
        var green = colourSensor.green()
        var blue = colourSensor.blue()

        var redInTol = abs(red - CS_RED_RGB[0]) <= CS_RED_TOLERANCE[0]
        var greenInTol = abs(green - CS_RED_RGB[1]) <= CS_RED_TOLERANCE[1]
        var blueInTol = abs(blue - CS_RED_RGB[2]) <= CS_RED_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "RED"
            return Colours.RED
        }

        redInTol = abs(red - CS_BLUE_RGB[0]) <= CS_BLUE_TOLERANCE[0]
        greenInTol = abs(green - CS_BLUE_RGB[1]) <= CS_BLUE_TOLERANCE[1]
        blueInTol = abs(blue - CS_BLUE_RGB[2]) <= CS_BLUE_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "BLUE"
            return Colours.BLUE
        }

        redInTol = abs(red - CS_YELLOW_RGB[0]) <= CS_YELLOW_TOLERANCE[0]
        greenInTol = abs(green - CS_YELLOW_RGB[1]) <= CS_YELLOW_TOLERANCE[1]
        blueInTol = abs(blue - CS_YELLOW_RGB[2]) <= CS_YELLOW_TOLERANCE[2]
        if (redInTol && greenInTol && blueInTol) {
            latestColour = "YELLOW"
            return Colours.YELLOW
        }

        latestColour = "NONE"
        return Colours.NONE
    }

    enum class Colours {
        RED,
        BLUE,
        YELLOW,
        NONE
    }
}