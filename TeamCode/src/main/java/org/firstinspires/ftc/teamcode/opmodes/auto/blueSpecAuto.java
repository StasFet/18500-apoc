package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.core.PIDTune.intake;
import static org.firstinspires.ftc.teamcode.opmodes.auto.BuildPaths.*;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.*;

import org.firstinspires.ftc.teamcode.commands.IntakeWaitForSample;
import org.firstinspires.ftc.teamcode.core.Robot;
import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.core.Commands;

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.subsystems.*;


public class blueSpecAuto extends CommandOpMode {
    private int pathState = 0;
    private Follower follower;
    private Timer pathTimer, actionTimer, opModeTimer;
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
        intake = new Intake(robot);
        outtake = new Outtake(robot);
        lift = new Lift(robot);
        CMD = new Commands(intake, outtake, lift);
    }

    @Override
    public void run(){}

    private void autonomousPathUpdates(){
        switch(pathState){
            case 0:
                new SequentialCommandGroup(
                    new InstantCommand(()->follower.followPath(startToBar))
                    //score specimen
                ).schedule();
                setPathState(1);
            case 1:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                        new InstantCommand(()->follower.followPath(barToSm1))
                    ).schedule();
                    setPathState(2);
                }
            case 2:
                if (!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(scoopSm1)).schedule();
                    setPathState(3);
                }
            case 3:
                if (!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(wallToSm2));
                    setPathState(4);
                }
            case 4:
                if (!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(scoopSm2));
                    setPathState(5);
                }
            case 5:
                if (!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(wallToSm3));
                    setPathState(6);
                }
            case 6:
                if(!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(scoopSm3));
                    //intake the specimen on the wall
                    setPathState(7);
                }
            case 7:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(
                            new InstantCommand(()->follower.followPath(wallToBar))
                            //score
                    ).schedule();
                    setPathState(8);
                }
            case 8:
                if(!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(slideSpecimensAcross));
                    setPathState(9);
                }
            case 9:
                if (!follower.isBusy()){
                    new InstantCommand(()->follower.followPath(barToWallApproach));
                    setPathState(10);
                }
            case 10:
                if (!follower.isBusy()){
                    new SequentialCommandGroup(
                        new InstantCommand(()->follower.followPath(wallApproachToWall))
                        //score specimen
                    ).schedule();
                    setPathState(7);
                }
        }
    }
}
