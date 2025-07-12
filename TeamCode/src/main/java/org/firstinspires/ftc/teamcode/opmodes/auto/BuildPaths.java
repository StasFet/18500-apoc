package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import static org.firstinspires.ftc.teamcode.opmodes.auto.Positions.*;

public class BuildPaths {


		public static PathBuilder builder = new PathBuilder();
		public static PathChain moveToScore = builder
				.addPath(
						new BezierLine(
								new Point(startPose),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm1 = builder
				.addPath(
						new BezierLine(
								new Point(scorePose),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-14))
				.build();

		public static PathChain scoreSm1 = builder
				.addPath(
						new BezierLine(
								new Point(scorePose),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-14), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm2 = builder
				.addPath(
						new BezierLine(
								new Point(scorePose),
								new Point(21.000, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(4))
				.build();

		public static PathChain scoreSm2 = builder
				.addPath(
						new BezierLine(
								new Point(21.000, 128.000, Point.CARTESIAN),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(4), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm3 = builder
				.addPath(
						new BezierLine(
								new Point(scorePose),
								new Point(27.000, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(32))
				.build();

		public static PathChain scoreSm3 = builder
				.addPath(
						new BezierLine(
								new Point(27.000, 128.000, Point.CARTESIAN),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(32), Math.toRadians(-45))
				.build();

		public static PathChain basketToSub = builder
				.addPath(
						new BezierCurve(
								new Point(scorePose),
								new Point(58.077, 123.315, Point.CARTESIAN),
								new Point(subPose1)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(270))
				.build();

		public static PathChain searchForSample = builder
				.addPath(
						new BezierLine(
								new Point(subPose1),
								new Point(subPose2)
						)
				)
				.setConstantHeadingInterpolation(Math.toRadians(270))
				.build();

		public static PathChain subToBasket = builder
				.addPath(
						new BezierCurve(
								new Point(subPose2),
								new Point(58.343, 123.315, Point.CARTESIAN),
								new Point(scorePose)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(-45))
				.build();
	public static PathChain startToBar = builder
			.addPath(
					new BezierLine(
							new Point(8.500, 72.000, Point.CARTESIAN),
							new Point(38.000, 72.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain barToSm1 = builder
			.addPath(
					new BezierCurve(
							new Point(38.000, 72.000, Point.CARTESIAN),
							new Point(9.900, 30.400, Point.CARTESIAN),
							new Point(71.000, 37.200, Point.CARTESIAN),
							new Point(61.500, 23.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain scoopSm1 = builder
			.addPath(
					new BezierLine(
							new Point(61.500, 23.000, Point.CARTESIAN),
							new Point(20.000, 23.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallToSm2 = builder
			.addPath(
					new BezierCurve(
							new Point(20.000, 23.000, Point.CARTESIAN),
							new Point(61.500, 22.800, Point.CARTESIAN),
							new Point(58.300, 25.700, Point.CARTESIAN),
							new Point(60.400, 12.900, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain scoopSm2 = builder
			.addPath(
					new BezierLine(
							new Point(60.400, 12.900, Point.CARTESIAN),
							new Point(20.000, 13.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallToSm3 = builder
			.addPath(
					new BezierCurve(
							new Point(20.000, 13.000, Point.CARTESIAN),
							new Point(64.177, 15.116, Point.CARTESIAN),
							new Point(60.000, 8.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain scoopSm3 = builder
			.addPath(
					new BezierLine(
							new Point(60.000, 8.000, Point.CARTESIAN),
							new Point(9.000, 8.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallToBar = builder
			.addPath(
					new BezierCurve(
							new Point(9.000, 8.000, Point.CARTESIAN),
							new Point(9.000, 70.000, Point.CARTESIAN),
							new Point(39.000, 70.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain slideSpecimensAcross = builder
			.addPath(
					new BezierLine(
							new Point(39.000, 70.000, Point.CARTESIAN),
							new Point(39.000, 68.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain barToWallApproach = builder
			.addPath(
					new BezierCurve(
							new Point(39.000, 68.000, Point.CARTESIAN),
							new Point(9.000, 70.000, Point.CARTESIAN),
							new Point(15.000, 8.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallApproachToWall = builder
			.addPath(
					new BezierLine(
							new Point(15.000, 8.000, Point.CARTESIAN),
							new Point(9.000, 8.000, Point.CARTESIAN)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();



}
