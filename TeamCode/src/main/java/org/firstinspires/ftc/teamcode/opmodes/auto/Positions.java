package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.Point;

// pose template
// public static final Pose template = new Pose(0, 0, Math.toRadians(0));

public class Positions {
	// SAMPLE AUTO ----------------
	/*
	* 	- Starting position
	*	- Basket position (1st sample)
	* 	- 2nd sample
	* 	- 3rd sample
	* 	- 4th sample
	*
	* 	- control points for 5th sample
	* 	- 5th sample/sub
	*
	* 	- park??
	*
	* */

	// SPECIMEN AUTO ----------------
	/*
	* 	- Starting position
	* 	- 1st spec bar
	* 	- control points to get above the preset samples
	* 	- stuff to push the samples into zone
	* 	- pick up spec
	* 	- 2nd spec bar
	* 	- pick up spec from bar (control points)
	* 	- 3rd-5th spec bar
	* 	- park??
	*
	* 	--POSES--
	* 	wall
	* 	bar
	* 	slide
	* 	wallApproach
	*
	* */
	public static final Pose wall = new Pose(9.000,8.000, Point.CARTESIAN);
	public static final Pose bar = new Pose(39.000,70.000,Point.CARTESIAN);
	public static final Pose slide = new Pose(39.000,68.000,Point.CARTESIAN);
	public static final Pose wallApproach = new Pose(15.000, 8.000, Point.CARTESIAN);
}