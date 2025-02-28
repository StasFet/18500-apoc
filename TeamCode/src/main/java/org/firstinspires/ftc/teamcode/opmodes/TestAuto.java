package org.firstinspires.ftc.teamcode.opmodes;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.opmodes.auto.*;

@Autonomous
public class TestAuto extends OpMode {
	MyRobot robot = new MyRobot(hardwareMap, gamepad1, gamepad2, telemetry);
	Follower follower = robot.follower;
	BuildPaths bp = new BuildPaths();
	int pathState = 0;

	private void pathUpdate() {
		switch (pathState) {
			case 0:
				follower.followPath(bp.move);
				pathState = 1;
		}
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
		Constants.setConstants(FConstants.class, LConstants.class);
		follower.setStartingPose(Positions.start);
	}
}
