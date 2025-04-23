package org.firstinspires.ftc.teamcode.commands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

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
