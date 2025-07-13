package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.core.Constants.ARM_LINK_IN;
import static org.firstinspires.ftc.teamcode.core.Constants.ARM_START;
import static org.firstinspires.ftc.teamcode.core.Constants.INTAKE_HOVER;
import static org.firstinspires.ftc.teamcode.core.Constants.INTAKE_START_POSITION;

import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.BezierPoint;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;

import org.firstinspires.ftc.teamcode.commands.AlwaysUpdateLiftPID;
import org.firstinspires.ftc.teamcode.commands.IntakeExtend;
import org.firstinspires.ftc.teamcode.commands.ZeroIntakeSlides;
import org.firstinspires.ftc.teamcode.commands.ZeroVerticalSlides;
import org.firstinspires.ftc.teamcode.core.Commands;
import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous
public class Goodna2 extends CommandOpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;
    private Commands CMD;
    private Intake intake;
    private Outtake outtake;
    private Lift lift;
    private MecanumDrive dt;
    private Robot robot;
    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;

    private final Pose startPose = new Pose(8.75, 113.25, Math.toRadians(0));
    private final Pose scorePose = new Pose(16, 127, Math.toRadians(315));
    private final Pose pickup1Pose = new Pose(16, 127, Math.toRadians(351));
    private final Pose pickup2Pose = new Pose(16, 127, Math.toRadians(9));
    private final Pose pickup3Pose = new Pose(21, 125, Math.toRadians(38));
    private final Pose parkPose = new Pose(60, 98, Math.toRadians(90));
    private final Pose parkControlPose = new Pose(60, 98, Math.toRadians(90));

    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;
    private boolean dropIntake;

    @Override
    public void initialize() {
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2);
        dt = new MecanumDrive(robot);
        robot.setColours(Intake.Colours.BLUE, Intake.Colours.YELLOW);
        intake = new Intake(robot);
        outtake = new Outtake(robot);
        lift = new Lift(robot);
        CMD = new Commands(intake, outtake, lift);
        //setup timers
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
        follower.setStartingPose(startPose);
        buildPaths();

        //init robot position
        intake.wristToPos(INTAKE_START_POSITION);
        intake.closeIntakeStopper();
        outtake.setPosition(ARM_START);
        outtake.setLinkagePos(ARM_LINK_IN);
        outtake.clawClose();
        boolean dropIntake = false;

        schedule(new SequentialCommandGroup(
                new InstantCommand(() -> follower.followPath(scorePreload)),
                new IntakeExtend(intake),
                new WaitUntilCommand(() -> !follower.isBusy()),
                //CMD.depositAndReturn(),
                new InstantCommand(() -> follower.followPath(grabPickup1, true)),
                new WaitUntilCommand(() -> Math.abs(follower.getCurrentPath().getClosestPointHeadingGoal()-follower.getPose().getHeading()) < 0.05),
                new InstantCommand(() -> follower.followPath(scorePickup1, true)),
                new WaitUntilCommand(() -> Math.abs(follower.getCurrentPath().getClosestPointHeadingGoal()-follower.getPose().getHeading()) < 0.05),
                new InstantCommand(() -> follower.followPath(grabPickup2, true)),
                new WaitUntilCommand(() -> Math.abs(follower.getCurrentPath().getClosestPointHeadingGoal()-follower.getPose().getHeading()) < 0.05),
                new InstantCommand(() -> follower.followPath(scorePickup2, true)),
                new WaitUntilCommand(() -> Math.abs(follower.getCurrentPath().getClosestPointHeadingGoal()-follower.getPose().getHeading()) < 0.05),
                new InstantCommand(() -> follower.followPath(grabPickup3, true)),
                new WaitUntilCommand(() -> !follower.isBusy()),
                new InstantCommand(() -> follower.followPath(scorePickup3, true)),
                new WaitUntilCommand(() -> !follower.isBusy())
                // Add additional commands here, e.g., for scoring or parking
        ));
    }

    private void buildPaths() {
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierPoint(new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierPoint(new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierPoint(new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierPoint(new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        park = new Path(new BezierCurve(new Point(scorePose), new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }

    @Override
    public void run() {
        super.run();
        if (!dropIntake) {
            intake.wristToPos(INTAKE_HOVER);
            dropIntake = true;
        }

        //pid
        //CommandScheduler.getInstance().schedule(new AlwaysUpdateLiftPID(lift));
        
        follower.update();
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.addData("heading", follower.getPose().getHeading());
        telemetry.update();
    }
}