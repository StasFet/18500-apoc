package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.opmodes.auto.Positions.bar;
import static org.firstinspires.ftc.teamcode.opmodes.auto.Positions.slide;
import static org.firstinspires.ftc.teamcode.opmodes.auto.Positions.wall;
import static org.firstinspires.ftc.teamcode.opmodes.auto.Positions.wallApproach;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;


public class BuildPaths {


		public static PathBuilder builder = new PathBuilder();
		/*
		   _____         __  __ _____  _      ______           _    _ _______ ____
		  / ____|  /\   |  \/  |  __ \| |    |  ____|     /\  | |  | |__   __/ __ \
		 | (___   /  \  | \  / | |__) | |    | |__       /  \ | |  | |  | | | |  | |
		  \___ \ / /\ \ | |\/| |  ___/| |    |  __|     / /\ \| |  | |  | | | |  | |
		  ____) / ____ \| |  | | |    | |____| |____   / ____ \ |__| |  | | | |__| |
		 |_____/_/    \_\_|  |_|_|    |______|______| /_/    \_\____/   |_|  \____/
		 */
		public static PathChain moveToScore = builder
				.addPath(
						new BezierLine(
								new Point(8.000, 112.000, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm1 = builder
				.addPath(
						new BezierLine(
								new Point(16.500, 128.000, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(-14))
				.build();

		public static PathChain scoreSm1 = builder
				.addPath(
						new BezierLine(
								new Point(16.500, 128.000, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-14), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm2 = builder
				.addPath(
						new BezierLine(
								new Point(16.500, 128.000, Point.CARTESIAN),
								new Point(21.000, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(4))
				.build();

		public static PathChain scoreSm2 = builder
				.addPath(
						new BezierLine(
								new Point(21.000, 128.000, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(4), Math.toRadians(-45))
				.build();

		public static PathChain angleForSm3 = builder
				.addPath(
						new BezierLine(
								new Point(16.500, 128.000, Point.CARTESIAN),
								new Point(27.000, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(32))
				.build();

		public static PathChain scoreSm3 = builder
				.addPath(
						new BezierLine(
								new Point(27.000, 128.000, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(32), Math.toRadians(-45))
				.build();

		public static PathChain basketToSub = builder
				.addPath(
						new BezierCurve(
								new Point(16.500, 128.000, Point.CARTESIAN),
								new Point(58.077, 123.315, Point.CARTESIAN),
								new Point(60.400, 95.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(-45), Math.toRadians(270))
				.build();

		public static PathChain searchForSample = builder
				.addPath(
						new BezierLine(
								new Point(60.400, 95.000, Point.CARTESIAN),
								new Point(67.000, 95.000, Point.CARTESIAN)
						)
				)
				.setConstantHeadingInterpolation(Math.toRadians(270))
				.build();

		public static PathChain subToBasket = builder
				.addPath(
						new BezierCurve(
								new Point(67.000, 95.000, Point.CARTESIAN),
								new Point(58.343, 123.315, Point.CARTESIAN),
								new Point(16.500, 128.000, Point.CARTESIAN)
						)
				)
				.setLinearHeadingInterpolation(Math.toRadians(270), Math.toRadians(-45))
				.build();
		/*

		   _____ _____  ______ _____           _    _ _______ ____
		  / ____|  __ \|  ____/ ____|     /\  | |  | |__   __/ __ \
		 | (___ | |__) | |__ | |         /  \ | |  | |  | | | |  | |
		  \___ \|  ___/|  __|| |        / /\ \| |  | |  | | | |  | |
		  ____) | |    | |___| |____   / ____ \ |__| |  | | | |__| |
		 |_____/|_|    |______\_____| /_/    \_\____/   |_|  \____/

		 */
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
							new Point(wall)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallToBar = builder
			.addPath(
					new BezierCurve(
							new Point(wall),
							new Point(9.000, 70.000, Point.CARTESIAN),
							new Point(bar)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain slideSpecimensAcross = builder
			.addPath(
					new BezierLine(
							new Point(bar),
							new Point(slide)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain barToWallApproach = builder
			.addPath(
					new BezierCurve(
							new Point(slide),
							new Point(9.000, 70.000, Point.CARTESIAN),
							new Point(wallApproach)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();

	public static PathChain wallApproachToWall = builder
			.addPath(
					new BezierLine(
							new Point(wallApproach),
							new Point(wall)
					)
			)
			.setConstantHeadingInterpolation(Math.toRadians(0))
			.build();


}
