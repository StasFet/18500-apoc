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

                ).schedule();
                setPathState(1);
            case 1:
                if(!follower.isBusy()){
                    new SequentialCommandGroup(

                    ).schedule();
                    setPathState(2);
                }
            case 2:
                if (!follower.isBusy()){
                    new SequentialCommandGroup(

                    ).schedule();
                    setPathState(3);
                }
        }
    }
}
