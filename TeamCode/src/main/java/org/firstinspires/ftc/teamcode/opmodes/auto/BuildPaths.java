package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathBuilder;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;


public class BuildPaths {


		public static PathBuilder builder = new PathBuilder();

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


}
