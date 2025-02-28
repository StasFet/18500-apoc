package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name = "ayush teleop", group = "teleop")
public class OpMode extends CommandOpMode {
    //core stuff
    private MyRobot robot;
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));

    //subsystems
    DriveTrain dt;

    //commands
    Drive drive;

    @Override
    public void initialize() {
        robot = new MyRobot(hardwareMap, gamepad1, gamepad2, telemetry);
        follower = robot.follower;
        dt = new DriveTrain(robot);

        drive = new Drive(dt, robot);

        follower.setStartingPose(startPose);
        dt.setDefaultCommand(drive);
    }
}
