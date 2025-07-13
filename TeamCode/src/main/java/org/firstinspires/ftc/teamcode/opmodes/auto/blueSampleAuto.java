package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.core.Constants.ARM_START;
import static org.firstinspires.ftc.teamcode.core.Constants.INTAKE_START_POSITION;
import static org.firstinspires.ftc.teamcode.core.PIDTune.intake;
import static org.firstinspires.ftc.teamcode.opmodes.auto.BuildPaths.*;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.*;

import org.firstinspires.ftc.teamcode.commands.IntakeWaitForSample;
import org.firstinspires.ftc.teamcode.core.Robot;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.localization.PoseUpdater;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.DashboardPoseTracker;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.teamcode.core.Commands;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.*;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

@Autonomous
public class blueSampleAuto extends CommandOpMode {
    private int pathState = 0;
    public Timer pathTimer = new Timer();
    private Commands CMD;
    private Intake intake;
    private Outtake outtake;
    private Lift lift;
    private MecanumDrive dt;
    private Robot robot;
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    @Override
    public void initialize(){
        CommandScheduler.getInstance().reset();
        robot = new Robot(hardwareMap, telemetry, gamepad1, gamepad2);
        dt = new MecanumDrive(robot);
        robot.setColours(Intake.Colours.BLUE, Intake.Colours.YELLOW);
        intake = new Intake(robot);
        outtake = new Outtake(robot);
        lift = new Lift(robot);
        CMD = new Commands(intake, outtake, lift);

        intake.wristToPos(INTAKE_START_POSITION);
        outtake.setPosition(ARM_START);
        outtake.setLinkagePos(ARM_LINK_IN);
        outtake.clawClose();

    }

    @Override
    public void run(){
        autonomousPathUpdates();
        telemetry.addData("pathState", pathState);
        telemetry.addData("pathTimer",pathTimer.getElapsedTimeSeconds());
    }

    private void autonomousPathUpdates(){
        Follower follower = new Follower(hardwareMap,FConstants.class,LConstants.class);
        switch(pathState){
            case 0:
                //new SequentialCommandGroup(
                    new InstantCommand(() -> follower.followPath(moveToScore));
//                    CMD.prepForBasket(),
//                    CMD.depositAndReturn()
//                ).schedule();
                setPathState(1);
            case 1:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                        new InstantCommand(() -> follower.followPath(angleForSm1)),
                        new IntakeWaitForSample(intake)
                    ).schedule();
                    setPathState(2);
                }
            case 2:
                if (!follower.isBusy()){
                    new SequentialCommandGroup(
                        new InstantCommand(() -> follower.followPath(scoreSm1))
                        //SCORE SAMPLE
                    ).schedule();
                    setPathState(3);
                }
            case 3:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                        new InstantCommand(()->follower.followPath(angleForSm2)),
                        new IntakeWaitForSample(intake)
                        ).schedule();
                    setPathState(4);
                }
            case 4:
                if (!follower.isBusy()){
                    new SequentialCommandGroup(
                            new InstantCommand(() -> follower.followPath(scoreSm2))
                            //SCORE SAMPLE
                    ).schedule();
                    setPathState(5);
                }
            case 5:
                if(!follower.isBusy()) {
                    new SequentialCommandGroup(
                            new InstantCommand(()->follower.followPath(angleForSm3)),
                            new IntakeWaitForSample(intake)
                    ).schedule();
                    setPathState(6);
                }
            case 6:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                            new InstantCommand(() -> follower.followPath(scoreSm3))
                            //SCORE SAMPLE
                    ).schedule();
                    setPathState(7);
                }
            /*
            case 7:
                if(!follower.isBusy()) {
                    follower.followPath(basketToSub);
                    setPathState(8);
                }
            case 8:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                            new InstantCommand(()->follower.followPath(searchForSample)),
                            new IntakeWaitForSample(intake)
                    ).schedule();
                    //INTAKE
                    setPathState(9);
                }
            case 9:
                if(!follower.isBusy()) {
                    follower.followPath(subToBasket);
                    //SCORE
                    setPathState(10);
                }
            case 10:
                if(!follower.isBusy()){
                    setPathState(10);
                }

             */
        }
    }
}
