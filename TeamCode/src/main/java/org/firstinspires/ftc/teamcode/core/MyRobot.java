package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.Robot;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.ServoEx;
import com.pedropathing.follower.Follower;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Intake.*;

import com.pedropathing.localization.GoBildaPinpointDriver;

public class MyRobot extends Robot {

    //declare core stuff
    public IMU imu;
    IMU.Parameters imu_params;
    public GoBildaPinpointDriver odo;
    Telemetry telemetry;
    public Follower follower;
    OpModeType opmode;
    public Intake.IntakeColour intakeColour;

    //sensors and other widgets
    public RevColorSensorV3 intakeColorSensor;

    //declare gamepads
    public GamepadEx gpGeneral;
    public GamepadEx gpDrive;

    //declare drivetrain + intake motors
    public DcMotorEx fr, fl, br, bl;
    public DcMotorEx intakeSpin;

    //declare slide motors
    public DcMotorEx vslide1, vslide2;
    public DcMotorEx intakeSlide;

    //declare servos
    public ServoEx hangServoLeft;
    public ServoEx hangServoRight;
    public ServoEx wristServo;
    public ServoEx clawServo;
    public ServoEx armRightServo;
    public ServoEx armLeftServo;

    public enum OpModeType {
        TELE_OP,
        AUTO
    }

    //pinpoint odometry set-up
    private void initPinpoint(HardwareMap hMap) {
        odo = hMap.get(GoBildaPinpointDriver.class, NAME_PINPOINT);
        odo.resetPosAndIMU();
        odo.setOffsets(0, 0);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);

    }
    private void initIMU(HardwareMap hMap) {
        //imu stuff - no longer needed because of pinpoint odo
        imu = hMap.get(IMU.class, NAME_IMU);
        imu_params = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(imu_params);
    }
    private void initGamepads(Gamepad gp1, Gamepad gp2) {
        //initialise gamepads
        gpGeneral = new GamepadEx(gp1);
        gpDrive = new GamepadEx(gp2);
    }
    private void initDriveMotors(HardwareMap hMap) {
        //initialise motors
        fr = hMap.get(DcMotorEx.class, NAME_FR);
        fl = hMap.get(DcMotorEx.class, NAME_FL);
        br = hMap.get(DcMotorEx.class, NAME_BR);
        bl = hMap.get(DcMotorEx.class, NAME_BL);
        br.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }
    private void initOtherMotors(HardwareMap hMap) {
        vslide1 = hMap.get(DcMotorEx.class, NAME_VSLIDE1);
        vslide2 = hMap.get(DcMotorEx.class, NAME_VSLIDE2);
        intakeSlide = hMap.get(DcMotorEx.class, NAME_INTSLIDE);
        intakeSpin = hMap.get(DcMotorEx.class, NAME_INTAKE);

        //reset encoders on relevant motors that will use ticks for velocity PIDs
        vslide1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        vslide2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intakeSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    private void initServos(HardwareMap hMap) {
        hangServoLeft = hMap.get(ServoEx.class, NAME_HANGL);
        hangServoRight = hMap.get(ServoEx.class, NAME_HANGR);

        wristServo = hMap.get(ServoEx.class, NAME_WRIST);
        clawServo = hMap.get(ServoEx.class, NAME_CLAW);
        armRightServo = hMap.get(ServoEx.class, NAME_ARMR);
        armLeftServo = hMap.get(ServoEx.class, NAME_ARML);

    }
    private void initSensors(HardwareMap hMap) {
        intakeColorSensor = hMap.get(RevColorSensorV3.class, NAME_COLOURSENSOR);
    }

    public MyRobot(HardwareMap hMap, Gamepad gamepad1, Gamepad gamepad2, Telemetry t, OpModeType type, IntakeColour colour) {
        opmode = type;
        follower = new Follower(hMap);
        this.intakeColour = colour;

        telemetry = t;

        if (opmode == OpModeType.TELE_OP) {
            initSensors(hMap);
            //initPinpoint(hMap);
            initIMU(hMap);
            initGamepads(gamepad1, gamepad2);
            initDriveMotors(hMap);
            //initOtherMotors(hMap);
            //initServos(hMap);
        } else if (opmode == OpModeType.AUTO) {
            // do stuff
        }

    }
}
