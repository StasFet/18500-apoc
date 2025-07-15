package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.core.Constants.ARM_DEPOSIT_SPECIMEN;
import static org.firstinspires.ftc.teamcode.core.Constants.ARM_LINK_IN;
import static org.firstinspires.ftc.teamcode.core.Constants.ARM_START;
import static org.firstinspires.ftc.teamcode.core.Constants.INTAKE_HOVER;
import static org.firstinspires.ftc.teamcode.core.Constants.INTAKE_START_POSITION;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.pedropathing.follower.Follower;
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

import org.firstinspires.ftc.teamcode.commands.AlwaysUpdateLiftPID;
import org.firstinspires.ftc.teamcode.commands.IntakeExtend;
import org.firstinspires.ftc.teamcode.commands.IntakeRetract;
import org.firstinspires.ftc.teamcode.core.Commands;
import org.firstinspires.ftc.teamcode.core.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Lift;
import org.firstinspires.ftc.teamcode.subsystems.MecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.Outtake;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

@Autonomous
public class GoodnaSpecAuto extends CommandOpMode {

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

	private final Pose startPose = new Pose(8.75, 65.250, Math.toRadians(0));
	private final Pose preloadScorePose = new Pose(39,65.250,Math.toRadians(0));
	private final Pose scorePose = new Pose(39, 70, Math.toRadians(0));
	private final Pose scoochPose = new Pose(39, 69, Math.toRadians(0));
	private final Pose scoreControlPose = new Pose(8.750, 70.000, Math.toRadians(0));
	private final Pose pickup1Pose = new Pose(61.500, 27.000, Math.toRadians(0));
	private final Pose pickup1Control1Pose = new Pose(9.900, 30.400, Math.toRadians(0));
	private final Pose pickup1Control2Pose = new Pose(67.000, 40.000, Math.toRadians(0));
	private final Pose dropoff1Pose = new Pose(18.000, 27.000, Math.toRadians(0));
	private final Pose pickup2Pose = new Pose(60.400, 16, Math.toRadians(0));
	private final Pose pickup2Control1Pose = new Pose(61.500, 22.800, Math.toRadians(0));
	private final Pose pickup2Control2Pose = new Pose(58.300, 25.700, Math.toRadians(0));
	private final Pose dropoff2Pose = new Pose(20.000, 16.000, Math.toRadians(0));
	private final Pose pickup3Pose = new Pose(60.000, 10.000, Math.toRadians(0));
	private final Pose pickup3ControlPose = new Pose(64.177, 15.116, Math.toRadians(0));
	private final Pose dropoff3Pose = new Pose(15,10,Math.toRadians(0));
	private final Pose parkPose = new Pose(9.750, 34.000, Math.toRadians(0));
	private final Pose parkControl1Pose = new Pose(28.000, 5.000, Math.toRadians(0));
	private final Pose parkControl2Pose = new Pose(20,38,Math.toRadians(0));
	private final Pose parkApproachPose = new Pose(15, 34.000, Math.toRadians(0));

	private Path scorePreload, park;
	private PathChain grabPickup1, grabPickup2, grabPickup3, dropoffPickup1, dropoffPickup2, dropoffPickup3, scoreSpecimen, scoochSpecimens, parkApproach, scoringPark;
	private boolean dropIntake;
	private void buildPaths() {
		scorePreload = new Path(new BezierLine(new Point(startPose), new Point(preloadScorePose)));
		scorePreload.setConstantHeadingInterpolation(Math.toRadians(0));

		grabPickup1 = follower.pathBuilder()
				.addPath(new BezierCurve(
						new Point(preloadScorePose),
						new Point(pickup1Control1Pose),
						new Point(pickup1Control2Pose),
						new Point(pickup1Pose)
				))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		dropoffPickup1 = follower.pathBuilder()
				.addPath(new BezierLine(new Point(pickup1Pose), new Point(dropoff1Pose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		grabPickup2 = follower.pathBuilder()
				.addPath(new BezierCurve(
						new Point(dropoff1Pose),
						new Point(pickup2Control1Pose),
						new Point(pickup2Control2Pose),
						new Point(pickup2Pose)
				))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		dropoffPickup2 = follower.pathBuilder()
				.addPath(new BezierLine(new Point(pickup2Pose),new Point(dropoff2Pose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		grabPickup3 = follower.pathBuilder()
				.addPath(new BezierCurve(
						new Point(dropoff2Pose),
						new Point(pickup3ControlPose),
						new Point(pickup3Pose)
				))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		dropoffPickup3 = follower.pathBuilder()
				.addPath(new BezierLine(new Point(pickup3Pose), new Point(dropoff3Pose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		scoreSpecimen = follower.pathBuilder()
				.addPath(new BezierCurve(new Point(parkPose),new Point(scoreControlPose), new Point(scorePose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		scoochSpecimens = follower.pathBuilder()
				.addPath(new BezierLine(new Point(scorePose), new Point(scoochPose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		parkApproach = follower.pathBuilder()
				.addPath(new BezierCurve(new Point(scoochPose),new Point(scoreControlPose), new Point(parkApproachPose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		//park path for whilst scoring - seperate from the park after collecting!!
		scoringPark = follower.pathBuilder()
				// ends at (9.75, 34)
				.addPath(new BezierLine(new Point(parkApproachPose), new Point(parkPose)))
				.setConstantHeadingInterpolation(Math.toRadians(0))
				.build();

		park = new Path(new BezierCurve(new Point(dropoff3Pose), new Point(parkControl1Pose), new Point(parkControl2Pose), new Point(parkPose)));
		park.setConstantHeadingInterpolation(Math.toRadians(0));
	}

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
		outtake.getClaw().setPosition(0.25);

		boolean dropIntake = false;

		Command score = new SequentialCommandGroup(
				new InstantCommand(() ->outtake.clawClose()),
				new WaitCommand(200),
				new ParallelCommandGroup(
						CMD.specHighBar(),
						new SequentialCommandGroup(
								new WaitCommand(200),
								new InstantCommand(()->follower.followPath(scoreSpecimen,true))
						)

				),
                new WaitUntilCommand(() -> !follower.isBusy()),
                new WaitCommand(200),
				// starts at scorePose
				new InstantCommand(()->follower.followPath(scoochSpecimens,true)),
				new WaitUntilCommand(() -> !follower.isBusy()),
				new InstantCommand(()->follower.followPath(parkApproach,true)),
				CMD.postSpecAuto(),
                new WaitUntilCommand(() -> !follower.isBusy()),
                new WaitCommand(200),
				// ends at parkPose
				new InstantCommand(()->follower.followPath(scoringPark,true)),
				new WaitUntilCommand(() -> !follower.isBusy()),
				new InstantCommand(() -> outtake.clawClose())
		);

		schedule(
				new ParallelCommandGroup(
						new AlwaysUpdateLiftPID(lift),
						new SequentialCommandGroup(
								new InstantCommand(() -> intake.brake()),
                                new ParallelCommandGroup(
                                        CMD.specHighBar(),
										new SequentialCommandGroup(
												new WaitCommand(500),
												new InstantCommand(() -> follower.followPath(scorePreload))
										)
                                ),
								new WaitUntilCommand(() -> !follower.isBusy()),
								//score preloaded specimen
								new WaitCommand(100),
								CMD.postSpecAuto(),
                                new WaitCommand(200),
								new InstantCommand(() -> follower.followPath(grabPickup1, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(() -> follower.followPath(dropoffPickup1, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(() -> follower.followPath(grabPickup2, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(() -> follower.followPath(dropoffPickup2, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(() -> follower.followPath(grabPickup3, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(() -> follower.followPath(dropoffPickup3, true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								new InstantCommand(()->follower.followPath(park,true)),
								new WaitUntilCommand(() -> !follower.isBusy()),
								//SCORE
								new InstantCommand(() -> outtake.clawClose()),
								score, score, score, score
								//Collect new sample
								// Add additional commands here, e.g., for scoring or parking
						)
				)

		);
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