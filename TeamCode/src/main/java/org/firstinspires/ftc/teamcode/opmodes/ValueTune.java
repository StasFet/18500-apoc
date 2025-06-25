package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@Config
@TeleOp(name="!Value Tuning")
public class ValueTune extends LinearOpMode {
	public static double pos = 0.5;
	public static double clawPos = 0.5;
	public static double armPos = 0.5;
	@Override
	public void runOpMode() {
		ServoImplEx right = hardwareMap.get(ServoImplEx.class, "temp");
		ServoImplEx left = hardwareMap.get(ServoImplEx.class, "temp2");
//		DcMotor r = hardwareMap.get(DcMotor.class, NAME_VSLIDER);
//		DcMotor l = hardwareMap.get(DcMotor.class, NAME_VSLIDEL);

		waitForStart();

		while (opModeIsActive()) {
			right.setPosition(armPos);
			left.setPosition(1-armPos);

			telemetry.addData("Arm Position:", armPos);
			telemetry.update();
		}
	}
}
