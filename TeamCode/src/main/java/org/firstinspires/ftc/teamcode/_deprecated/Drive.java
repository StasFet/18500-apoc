package org.firstinspires.ftc.teamcode._deprecated;

import com.arcrobotics.ftclib.command.CommandBase;

public class Drive extends CommandBase {
	private DriveTrain dt;

	public Drive(DriveTrain dt_p, MyRobot robot_p) {
		dt = dt_p;
		addRequirements(dt);
	}

	@Override
	public void execute() {
		dt.rc_drive();
	}

	@Override
	public boolean isFinished() {
		return false;
	}
}
