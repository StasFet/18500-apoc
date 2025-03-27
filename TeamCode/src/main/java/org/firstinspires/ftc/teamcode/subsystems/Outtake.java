package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.ServoEx;

import org.firstinspires.ftc.teamcode.core.MyRobot;

public class Outtake extends SubsystemBase {
	private MyRobot robot;
	private ServoEx claw;
	private ServoEx wrist;
	private ServoEx armL;
	private ServoEx armR;

	public Outtake (MyRobot robot) {
		this.robot = robot;
		this.claw = robot.clawServo;
		this.wrist = robot.wristServo;
		this.armL = robot.armLeftServo;
		this.armR = robot.armRightServo;
	}

	public void clawOpen() {
		claw.setPosition(CLAW_OPEN);
	}

	public void clawClose() {
		claw.setPosition(CLAW_CLOSED);
	}

	public void armToPos(double pos) {
		armL.setPosition(1-pos+ARM_OFFSET);
		armR.setPosition(pos);
	}

	public void wristToPos(double pos) {
		wrist.setPosition(pos);
	}

	public void armIdle() {
		armToPos(ARM_IDLE);
	}

	public void armTransfer() {
		armToPos(ARM_TRANSFER);
	}

	public void armRise() {
		armToPos(ARM_RISE);
	}

	public void armDeposit() {
		armToPos(ARM_DEPOSIT);
	}

	public void wristSpecWall() {
		wristToPos(WRIST_SPEC_WALL);
	}

	public void wristSpecBar() {
		wristToPos(WRIST_SPEC_BAR);
	}

	public void wristTransfer() {
		wristToPos(WRIST_TRANSFER);
	}

}
