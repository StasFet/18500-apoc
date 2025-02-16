package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.core.MyRobot;

public class DriveTrain extends SubsystemBase {

	private DcMotorEx fr;
	private DcMotorEx fl;
	private DcMotorEx br;
	private DcMotorEx bl;
	private GamepadEx gp;

	GoBildaPinpointDriver odo;

	public DriveTrain(MyRobot robot) {
		fr = robot.fr;
		fl = robot.fl;
		br = robot.br;
		bl = robot.bl;
		odo = robot.odo;
		gp = robot.gp_drive;

		fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
		bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

		fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
		bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
	}

	public void drive() {
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
	}
}
