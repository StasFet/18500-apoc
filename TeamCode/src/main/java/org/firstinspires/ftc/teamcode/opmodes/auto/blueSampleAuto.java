package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.opmodes.auto.BuildPaths.*;
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

import pedroPathing.constants.FConstants;
import pedroPathing.constants.LConstants;

public class blueSampleAuto {
    private int pathState = 0;
    private Follower follower;
    private Timer pathTimer, actionTimer, opModeTimer;
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }
    public void autonomousPathUpdates(){
        switch(pathState){
            case 0:
                follower.followPath(moveToScore);
                //SCORE PRELOAD SAMPLE
                setPathState(1);
            case 1:
                if(!follower.isBusy()){
                    follower.followPath(angleForSm1);
                    //INTAKE SAMPLE
                    setPathState(2);
                }
            case 2:
                if (!follower.isBusy()){
                    follower.followPath(scoreSm1);
                    //SCORE SAMPLE
                    setPathState(3);
                }
        }
    }
}
