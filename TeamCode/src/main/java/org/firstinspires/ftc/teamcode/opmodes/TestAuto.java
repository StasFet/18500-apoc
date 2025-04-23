package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import org.firstinspires.ftc.teamcode._deprecated.MyRobot;
import org.firstinspires.ftc.teamcode.opmodes.auto.*;
import org.firstinspires.ftc.teamcode._deprecated.Intake;

@Autonomous(name = "Blue Auto")
public class TestAuto extends OpMode {
	MyRobot robot = new MyRobot(hardwareMap, gamepad1, gamepad2, telemetry, MyRobot.OpModeType.AUTO, Intake.IntakeColour.BLUEYELLOW);
	Follower follower = robot.follower;
	BuildPaths bp = new BuildPaths();
	ElapsedTime timer = new ElapsedTime();
	int pathState;

	private void pathUpdate() {
		switch (pathState) {
			case 0:
				follower.followPath(bp.move);
				pathState = 1;
				break;
			case 1:
				if (!follower.isBusy()) {
					// do some other stuff!!
					pathState = -1;		//end
				}
		}
	}

	private void setPathState(int state) {
		pathState = state;
		timer.reset();
	}

	@Override
	public void loop() {
		follower.update();
		telemetry.addData("x", follower.getPose().getX());
		telemetry.addData("y", follower.getPose().getY());
		telemetry.addData("heading", follower.getPose().getHeading());
		telemetry.update();
		pathUpdate();
	}

	@Override
	public void init() {
		pathState = 0;
		Constants.setConstants(FConstants.class, LConstants.class);
		//follower.setStartingPose(Positions.start);
	}
}
