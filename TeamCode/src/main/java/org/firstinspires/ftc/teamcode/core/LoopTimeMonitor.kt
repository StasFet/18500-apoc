package org.firstinspires.ftc.teamcode.core

import com.qualcomm.robotcore.util.ElapsedTime

class LoopTimeMonitor() {
    private var timer: ElapsedTime = ElapsedTime()

    public fun startTimer() { timer = ElapsedTime() }

    public fun checkTime(): Long {
        val time: Long = timer.nanoseconds()
        timer.reset()
        return time
    }
}