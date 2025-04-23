package org.firstinspires.ftc.teamcode.commands;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.pedropathing.localization.GoBildaPinpointDriver;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;

import org.firstinspires.ftc.teamcode._deprecated.Drive;
import org.firstinspires.ftc.teamcode._deprecated.DriveTrain;
import org.firstinspires.ftc.teamcode._deprecated.Intake;
import org.firstinspires.ftc.teamcode._deprecated.MyRobot;

import pedroPathing.constants.*;

@TeleOp(name = "APOC teleop", group = "teleop")
@Disabled
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
        //odoThread = new OdoRefresh();

        //intake = new Intake(robot);
        dt = new DriveTrain(robot);
        drive = new Drive(dt, robot);

        Constants.setConstants(FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);

        register(intake, dt);
        dt.setDefaultCommand(drive);
    }


    @Override
    public void run() {
        pinpoint.update();  //change to thread if doesn't work
        follower.update();
        runtimeControls();
        super.run();
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
