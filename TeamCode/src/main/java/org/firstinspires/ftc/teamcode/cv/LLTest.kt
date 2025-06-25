package org.firstinspires.ftc.teamcode.cv

import com.qualcomm.hardware.limelightvision.Limelight3A
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class LLTest : LinearOpMode() {
    override fun runOpMode() {
        val ll3a: Limelight3A = hardwareMap.get(Limelight3A::class.java, "ll3a")
        ll3a.start()
        waitForStart()
        if (isStopRequested) return

        while (opModeIsActive()) {
            val res = ll3a.latestResult
            telemetry.addData("a", res.botpose)
        }
    }
}