package org.firstinspires.ftc.teamcode._deprecated;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.pedropathing.localization.GoBildaPinpointDriver;

public class DriveTrain extends SubsystemBase {

	private DcMotorEx fr;
	private DcMotorEx fl;
	private DcMotorEx br;
	private DcMotorEx bl;
	private GamepadEx gp;
	MyRobot robot;

	GoBildaPinpointDriver odo;

	public DriveTrain(MyRobot robot) {
		fr = robot.fr;
		fl = robot.fl;
		br = robot.br;
		bl = robot.bl;
		odo = robot.odo;
		gp = robot.gpDrive;
		this.robot = robot;

		fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	public void fc_drive() {
		double y = -gp.getLeftY();
		double x = gp.getLeftX();
		double rx = gp.getRightX();

		odo.update();
		double heading = odo.getHeading();

		double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
		double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

		rotX *= TURN_COEFFICIENT;

		double denominator = Math.max(Math.abs(rotX) + Math.abs(rotY) + Math.abs(rx), 1);
		double fl_power = (rotY + rotX + rx) / denominator;
		double bl_power = (rotY - rotX + rx) / denominator;
		double fr_power = (rotY - rotX - rx) / denominator;
		double br_power = (rotY + rotX - rx) / denominator;

		fr.setPower(fr_power);
		fl.setPower(fl_power);
		br.setPower(br_power);
		bl.setPower(bl_power);
		followerDrive();
	}

	public void rc_drive() {
		double y = gp.getLeftY();
		double x = gp.getLeftX() * 1.1; // Counteract imperfect strafing
		double rx = gp.getRightX();

		double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
		double frontLeftPower = (y + x + rx) / denominator;
		double backLeftPower = (y - x + rx) / denominator;
		double frontRightPower = (y - x - rx) / denominator;
		double backRightPower = (y + x - rx) / denominator;

		fl.setPower(frontLeftPower * REAR_DT_MULTI);
		bl.setPower(backLeftPower * REAR_DT_MULTI);
		fr.setPower(frontRightPower * FRONT_DT_MULTI);
		br.setPower(backRightPower * FRONT_DT_MULTI);
	}

	public void followerDrive() {
		robot.follower.setTeleOpMovementVectors(-robot.gpDrive.getLeftY(), -robot.gpDrive.getLeftX(), -robot.gpDrive.getRightX(), false);
		robot.follower.update();
	}
}
