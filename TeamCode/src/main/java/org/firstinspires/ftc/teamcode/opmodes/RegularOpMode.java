package org.firstinspires.ftc.teamcode.opmodes;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name = "APOC teleop", group = "teleop")
public class RegularOpMode extends CommandOpMode {
    //core stuff
    private MyRobot robot;
    private GoBildaPinpointDriver pinpoint;
    private Follower follower;
    private final Pose startPose = new Pose(0, 0, Math.toRadians(0));
    private OdoRefresh odoThread;

    //subsystems
    DriveTrain dt;

    //commands
    Drive drive;

    @Override
    public void initialize() {
        robot = new MyRobot(hardwareMap, gamepad1, gamepad2, telemetry, MyRobot.OpModeType.TELE_OP);
        follower = robot.follower;
        pinpoint = robot.odo;
        odoThread = new OdoRefresh();

        dt = new DriveTrain(robot);
        drive = new Drive(dt, robot);

        follower.setStartingPose(startPose);
        odoThread.start();
    }


    @Override
    public void run() {
        follower.startTeleopDrive();
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();
    }

    private class OdoRefresh extends Thread {
        @Override
        public void run() {
            while (UPDATE_ODO) {
                if (pinpoint != null) pinpoint.update();
            }
        }
    }
}
