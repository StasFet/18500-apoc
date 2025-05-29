package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Config
@TeleOp(name="!Value Tuning")
public class ValueTune extends LinearOpMode {
	public static double pos = 0.5;
	//public static double clawPos = 0.5;
	//public static double linkPos = 0.5;
	@Override
	public void runOpMode() {
//		ServoImplEx right = hardwareMap.get(ServoImplEx.class, NAME_ARMR);
//		ServoImplEx left = hardwareMap.get(ServoImplEx.class, NAME_ARML);
//		ServoImplEx claw = hardwareMap.get(ServoImplEx.class, NAME_CLAW);
//		ServoImplEx linkage = hardwareMap.get(ServoImplEx.class, NAME_ARM_LINKAGE);
		Robot robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2);
		robot.setColours(Intake.Colours.YELLOW);

		Intake intake = new Intake(robot);
		waitForStart();

		while (opModeIsActive()) {
			intake.wristToPos(pos);

			telemetry.addData("Arm Position:", pos);
			telemetry.update();
		}
	}
}
