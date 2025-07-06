package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.arcrobotics.ftclib.controller.PIDController;
import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Config
@TeleOp(name="!Value Tuning")
public class ValueTune extends LinearOpMode {
	//public static double pos = 0.5;
	public static double clawPos = 0.5;
	public static double armPos = 0.5;
	public static double intakePos = 0.5;
	public static double stopperPos = 0.5;
	public static double intakeSpinnyPos = 0;
	public static double intakeSlidesPos = 0;
	//public static double vSlidesRightPos = 0;
	public static double armLinkage = 0.5;

	public static double p = 5.5;
	public static double i = 0.01;
	public static double d = 0.01;

	@Override
	public void runOpMode() {
		ServoImplEx rightArm = hardwareMap.get(ServoImplEx.class, "arm_right");
		ServoImplEx leftArm = hardwareMap.get(ServoImplEx.class, "arm_left");

		DcMotor intakeSpinny = hardwareMap.get(DcMotorEx.class, "intake");

		DcMotorEx intakeSlide = hardwareMap.get(DcMotorEx.class, "hslide");
		intakeSlide.setTargetPosition(0);
		intakeSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ServoImplEx rightIntake = hardwareMap.get(ServoImplEx.class, "intakeRight");
		ServoImplEx leftIntake = hardwareMap.get(ServoImplEx.class, "intakeLeft");

		ServoImplEx intakeStopper = hardwareMap.get(ServoImplEx.class, "intakeStop");

		//DcMotorEx vSlideR = hardwareMap.get(DcMotorEx.class, "vslideRight");
		//vSlideR.setTargetPosition(0);
		//vSlideR.setMode(DcMotor.RunMode.RUN_TO_POSITION);

		ServoImplEx claw = hardwareMap.get(ServoImplEx.class, "claw");
		ServoImplEx linkage = hardwareMap.get(ServoImplEx.class, "armLinkage");

		PIDController PIDController = new PIDController(p, i, d);


		//val intake: DcMotorEx = robot.intakeSpin
		//val slide: DcMotorEx = robot.intakeSlide
		//val leftServo: Servo = robot.intakeLeft
		//val rightServo: Servo = robot.intakeRight
		//val colourSensor: RevColorSensorV3 = robot.intakeColorSensor
		//val intakeStopper: Servo = robot.intakeStopper;


//		DcMotor r = hardwareMap.get(DcMotor.class, NAME_VSLIDER);
//		DcMotor l = hardwareMap.get(DcMotor.class, NAME_VSLIDEL);

		waitForStart();

		while (opModeIsActive()) {
			rightArm.setPosition(armPos);
			leftArm.setPosition(1-armPos);

			telemetry.addData("Arm Position:", armPos);
			telemetry.update();

			rightIntake.setPosition(intakePos);
			leftIntake.setPosition(1-intakePos);

			telemetry.addData("Intake:", intakePos);
			telemetry.update();

			intakeStopper.setPosition(stopperPos);
			telemetry.addData("Stopper:", stopperPos);
			telemetry.update();

			intakeSlide.setTargetPosition((int) intakeSlidesPos);
			intakeSlide.setPower(0.5);
			telemetry.addData("Intake Slides: ", intakeSlidesPos);
			telemetry.update();

			//vSlideR.setTargetPosition((int) vSlidesRightPos);
			//vSlideR.setPower(0.5);
			//telemetry.addData("Vertical Slides Right Motor:", vSlidesRightPos);
			//telemetry.update();

			linkage.setPosition(armLinkage);
			telemetry.addData("Linkage: ", armLinkage);
			telemetry.update();

			intakeSpinny.setPower(intakeSpinnyPos);
			telemetry.addData("Intake Motor: ", intakeSpinnyPos);
			telemetry.update();

			claw.setPosition(clawPos);
			telemetry.addData("Claw: ", clawPos);
			telemetry.update();

			intakeSlide.setTargetPosition((int) intakeSlidesPos);
			intakeSlidesPos = intakeSlide.getCurrentPosition();
			telemetry.addData("Intake Slides Error Testing", intakeSlidesPos);


			//vSlideR.setTargetPosition((int) vSlidesRightPos);
			//vSlidesRightPos = vSlideR.getCurrentPosition();
			//telemetry.addData("Vertical Slides Error testing", vSlidesRightPos);
			//telemetry.update();
			telemetry.addData("P in PID):", p);
			telemetry.addData("I in PID:", i);
			telemetry.addData("D in PID:", d);
			telemetry.update();


		}
	}
}
