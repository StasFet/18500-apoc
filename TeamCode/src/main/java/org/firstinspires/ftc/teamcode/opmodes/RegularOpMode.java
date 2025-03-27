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
    private boolean odoThreadOn = false;

    //subsystems
    DriveTrain dt;
    Intake intake;

    //commands
    Drive drive;

    @Override
    public void initialize() {
        robot = new MyRobot(hardwareMap, gamepad1, gamepad2, telemetry, MyRobot.OpModeType.TELE_OP, Intake.IntakeColour.BLUEYELLOW);
        follower = robot.follower;
        pinpoint = robot.odo;
        odoThread = new OdoRefresh();

        dt = new DriveTrain(robot);
        intake = new Intake(robot);
        drive = new Drive(dt, robot);

        follower.setStartingPose(startPose);
    }


    @Override
    public void run() {
        pinpoint.update();  //change to thread if doesn't work
        follower.startTeleopDrive();
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, false);
        follower.update();
        runtimeControls();
    }

    private void runtimeControls() {
        switch (intake.state) {
            case IDLE:
                //if button pressed, schedule IntakeExtendAndReady
                break;
            case EXTENDED:
                //if button pressed, schedule IntakeDetectAndRetract
                break;
            case TRANSFER:
                //if button pressed, schedule TransferAndReady
            default:
                break;
        }
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
