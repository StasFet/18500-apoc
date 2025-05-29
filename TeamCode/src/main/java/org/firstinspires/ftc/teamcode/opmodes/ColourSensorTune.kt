package org.firstinspires.ftc.teamcode.opmodes

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
    override fun runOpMode() {
        colourSensor = hardwareMap.get(RevColorSensorV3::class.java, NAME_COLOURSENSOR)
        colourSensor.initialize()
        waitForStart()
        while (opModeIsActive()) {
            if (!colourSensor.isLightOn) colourSensor.enableLed(true)
            telemetry.addData("red", colourSensor.red())
            telemetry.addData("blue", colourSensor.blue())
            telemetry.addData("green", colourSensor.green())
            var out = ""
            if (checkColour(Colours.RED)) out = "RED"
            else if (checkColour(Colours.BLUE)) out = "BLUE"
            else if (checkColour(Colours.YELLOW)) out = "YELLOW"
            else out = "NONE"
            telemetry.addData("Colour Detected", out)
            telemetry.update()
        }
    }

    fun checkColour(colour: Colours): Boolean {
        var green = colourSensor.green()
        var red = colourSensor.red()
        var blue = colourSensor.blue()

//        when (colour) {
//            Colours.RED -> {
//                val redInTol = abs(red - CS_RED_RGB[0]) <= CS_RED_TOLERANCE[0]
//                val greenInTol = abs(green - CS_RED_RGB[1]) <= CS_RED_TOLERANCE[1]
//                val blueInTol = abs(blue - CS_RED_RGB[2]) <= CS_RED_TOLERANCE[2]
//                return redInTol && greenInTol && blueInTol
//            }
//
//            Colours.BLUE -> {
//                val redInTol = abs(red - CS_BLUE_RGB[0]) <= CS_BLUE_TOLERANCE[0]
//                val greenInTol = abs(green - CS_BLUE_RGB[1]) <= CS_BLUE_TOLERANCE[1]
//                val blueInTol = abs(blue - CS_BLUE_RGB[2]) <= CS_BLUE_TOLERANCE[2]
//                return redInTol && greenInTol && blueInTol
//            }
//
//            Colours.YELLOW -> {
//                val redInTol = abs(red - CS_YELLOW_RGB[0]) <= CS_YELLOW_TOLERANCE[0]
//                val greenInTol = abs(green - CS_YELLOW_RGB[1]) <= CS_YELLOW_TOLERANCE[1]
//                val blueInTol = abs(blue - CS_YELLOW_RGB[2]) <= CS_YELLOW_TOLERANCE[2]
//                return redInTol && greenInTol && blueInTol
//            }
        return false
    }
}