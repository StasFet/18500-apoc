package org.firstinspires.ftc.teamcode.core

import com.pedropathing.follower.Follower
import com.pedropathing.localization.GoBildaPinpointDriver
import com.qualcomm.hardware.rev.*
import com.qualcomm.robotcore.hardware.*
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.core.Constants.*
import pedroPathing.constants.*
import com.arcrobotics.ftclib.gamepad.GamepadEx
import com.arcrobotics.ftclib.hardware.motors.CRServo
import org.firstinspires.ftc.teamcode.subsystems.*

class Robot(val hMap: HardwareMap, val telemetry: Telemetry, val g1: Gamepad, val g2: Gamepad) {

    // core stuff
    val imu: IMU by lazy { hMap[NAME_IMU] as IMU }
    val odo: GoBildaPinpointDriver by lazy { hMap[NAME_PINPOINT] as GoBildaPinpointDriver }
    val follower: Follower by lazy { Follower(hMap, FConstants::class.java, LConstants::class.java) }
    val t: Telemetry = telemetry
    lateinit var cols: Array<Intake.Colours>

    // gamepads
    val gpGeneral: GamepadEx by lazy { GamepadEx(g1) }
    val gpDrive: GamepadEx by lazy { GamepadEx(g2) }

    // motors
    val fr: CachingMotorEx by lazy { CachingMotorEx(hMap, NAME_FR) }
    val fl: CachingMotorEx by lazy { CachingMotorEx(hMap, NAME_FL) }
    val br: CachingMotorEx by lazy { CachingMotorEx(hMap, NAME_BR) }
    val bl: CachingMotorEx by lazy { CachingMotorEx(hMap, NAME_BL) }

    val intakeSpin: DcMotorEx by lazy { hMap[NAME_INTAKE] as DcMotorEx }
    val intakeSlide: DcMotorEx by lazy { hMap[NAME_INTSLIDE] as DcMotorEx }

    val vslidel: DcMotorEx by lazy { hMap[NAME_VSLIDEL] as DcMotorEx }
    val vslider: DcMotorEx by lazy { hMap[NAME_VSLIDER] as DcMotorEx }

    // servos
    val hangRight: CRServo by lazy { hMap[NAME_HANGRIGHT] as CRServo }
    val hangLeft: CRServo by lazy { hMap[NAME_HANGLEFT] as CRServo }
    val claw: ServoImplEx by lazy { hMap[NAME_CLAW] as ServoImplEx }
    val armLeft: ServoImplEx by lazy { hMap[NAME_ARML] as ServoImplEx }
    val armRight: ServoImplEx by lazy { hMap[NAME_ARMR] as ServoImplEx }
    val armLinkage: ServoImplEx by lazy { hMap[NAME_ARM_LINKAGE] as ServoImplEx }
    val intakeLeft: Servo by lazy { hMap[NAME_INTAKELEFT] as Servo}
    val intakeRight: Servo by lazy { hMap[NAME_INTAKERIGHT] as Servo }
    val intakeStopper: Servo by lazy { hMap[NAME_INTAKE_STOP] as Servo}

    // sensors
    val intakeColorSensor: RevColorSensorV3 by lazy { hMap[NAME_COLOURSENSOR] as RevColorSensorV3 }
    val hangRightEncoder: AnalogInput by lazy { hMap[NAME_HANGRIGHTENC] as AnalogInput }
    val hangLeftEncoder: AnalogInput by lazy { hMap[NAME_HANGRIGHTENC] as AnalogInput }

    init {
        initPinpoint()
        initIMU()
        initDrive()
        // initSensors()
    }

    private fun initPinpoint() {
        odo.resetPosAndIMU()
        odo.setOffsets(0.0, 0.0)
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
        odo.setEncoderDirections(
            GoBildaPinpointDriver.EncoderDirection.FORWARD,
            GoBildaPinpointDriver.EncoderDirection.FORWARD
        )
        odo.update()
    }

    private fun initIMU() {
        val params: IMU.Parameters = IMU.Parameters(RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD
        ))
        imu.initialize(params)
    }

    private fun initDrive() {
        fr.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        fl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        br.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        bl.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun setColours(vararg cols: Intake.Colours) {
        this.cols = emptyArray()
        for (col in cols) {
            this.cols += col
        }
    }
}