package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.core.Constants.*
import kotlin.math.abs

class CachedMotorEx(hardwareMap: HardwareMap, name: String): DcMotorEx by hardwareMap.get(DcMotorEx::class.java, name) {

    private val motor: DcMotorEx = hardwareMap.get(DcMotorEx::class.java, name)
    private var lastPower: Double = 0.0
    public var threshold: Double = CACHE_THRESHOLD

    override fun setPower(power: Double) {
        if (abs(lastPower - power) > threshold) {
            motor.power = power
            lastPower = power
        } else if (lastPower == 0.0 && power != 0.0 || lastPower != 0.0 && power == 0.0) {
            motor.power = power
            lastPower = power
        }
    }
}