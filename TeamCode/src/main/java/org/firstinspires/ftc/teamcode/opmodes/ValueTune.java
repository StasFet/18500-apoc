package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Config
@TeleOp(name="!Value Tuning")
public class ValueTune extends LinearOpMode {
	public static double pos = 0.5;
	public static double intakeWristPos = 0.5;
	public static double intakeTargetPos = 0;
	public static double clawPos = 0.5;
	public static double intakePower = 0.0;
	@Override
	public void runOpMode() {
		ServoImplEx right = hardwareMap.get(ServoImplEx.class, NAME_ARMR);
		ServoImplEx left = hardwareMap.get(ServoImplEx.class, NAME_ARML);
		ServoImplEx claw = hardwareMap.get(ServoImplEx.class, NAME_CLAW);
//		ServoImplEx linkage = hardwareMap.get(ServoImplEx.class, NAME_ARM_LINKAGE);

		Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2);
		robot.setColours(Intake.Colours.YELLOW);

		Intake intake = new Intake(robot);
		waitForStart();

		while (opModeIsActive()) {
			right.setPosition(pos);
			left.setPosition(1-pos);
			intake.getIntake().setPower(intakePower);
			intake.getController().setSetPoint(intakeTargetPos);
			intake.updateVelocity();

			intake.wristToPos(intakeWristPos);

			claw.setPosition(clawPos);

			telemetry.addData("Arm Position:", pos);
			telemetry.update();
		}
	}
}
