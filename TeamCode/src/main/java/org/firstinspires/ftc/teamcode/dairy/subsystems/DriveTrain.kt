package org.firstinspires.ftc.teamcode.dairy.subsystems

import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import dev.frozenmilk.dairy.core.FeatureRegistrar
import dev.frozenmilk.dairy.core.dependency.Dependency
import dev.frozenmilk.dairy.core.dependency.annotation.SingleAnnotation
import dev.frozenmilk.dairy.core.wrapper.Wrapper
import dev.frozenmilk.mercurial.commands.Lambda
import dev.frozenmilk.mercurial.subsystems.Subsystem
import org.firstinspires.ftc.teamcode.core.Constants.*
import java.lang.annotation.Inherited
import kotlin.math.abs
import kotlin.math.max

object DriveTrain: Subsystem {
    @Target(AnnotationTarget.CLASS)
    @Retention(AnnotationRetention.RUNTIME)
    @MustBeDocumented
    @Inherited

    annotation class Attach

    override var dependency: Dependency<*> = Subsystem.DEFAULT_DEPENDENCY and SingleAnnotation(Attach::class.java)

    private val fl: DcMotorEx by subsystemCell { FeatureRegistrar.activeOpMode.hardwareMap.get(DcMotorEx::class.java, NAME_FL) }
    private val bl: DcMotorEx by subsystemCell { FeatureRegistrar.activeOpMode.hardwareMap.get(DcMotorEx::class.java, NAME_BL) }
    private val fr: DcMotorEx by subsystemCell { FeatureRegistrar.activeOpMode.hardwareMap.get(DcMotorEx::class.java, NAME_FR) }
    private val br: DcMotorEx by subsystemCell { FeatureRegistrar.activeOpMode.hardwareMap.get(DcMotorEx::class.java, NAME_BR) }
    private val gp: GamepadEx by subsystemCell { GamepadEx(FeatureRegistrar.activeOpMode.gamepad1) }

    // init code (constructor in FTCLib)
    override fun preUserInitHook(opMode: Wrapper) {
        init()
    }

    fun rcDrive(): Lambda {
        return Lambda("rcDrive")
            .addRequirements(this)
            .setInit { init() }
            .setExecute { rcDriveExecute() }
            .setFinish { false }
            .setInterruptible(false)
            .setRunStates(Wrapper.OpModeState.ACTIVE)
    }

    private fun init() {
        fl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        bl.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        fr.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        br.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER

        fl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        fr.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        bl.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        br.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
    }

    private fun rcDriveExecute() {
        //if (FeatureRegistrar.activeOpMode.) //extra check to not run during init((
        val y = gp.leftY
        val x = gp.leftX * 1.1 // Counteract imperfect strafing
        val rx = gp.rightX

        val denominator = max(abs(y) + abs(x) + abs(rx), 1.0)
        val frontLeftPower = (y + x + rx) / denominator
        val backLeftPower = (y - x + rx) / denominator
        val frontRightPower = (y - x - rx) / denominator
        val backRightPower = (y + x - rx) / denominator

        fl.power = frontLeftPower * FRONT_DT_MULTI
        bl.power = backLeftPower * REAR_DT_MULTI
        fr.power = frontRightPower * FRONT_DT_MULTI
        br.power = backRightPower * REAR_DT_MULTI
    }
}