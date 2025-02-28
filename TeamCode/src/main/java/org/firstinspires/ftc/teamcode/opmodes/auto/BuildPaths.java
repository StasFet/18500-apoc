package org.firstinspires.ftc.teamcode.opmodes.auto;

import com.pedropathing.follower.Follower;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import org.firstinspires.ftc.teamcode.opmodes.auto.*;

public class BuildPaths {
	public static Path move;

	public BuildPaths() {
		move = new Path(new BezierLine(Positions.start, Positions.end));
	}
}
