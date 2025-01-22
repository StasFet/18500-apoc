package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name = "ayush teleop", group = "teleop")
public class OpMode extends CommandOpMode {
    //core stuff
    private MyRobot myRobot;

    //subsystems
    private DriveTrain dt;
    private VertSlides vs;
    private IntakeSlides is;

    //state commands
    private VertSlidesUp vs_up;
    private VertSlidesDown vs_down;

    //default commands
    private VertSlidesRun vs_run;
    private IntakeSlidesRun is_run;

    @Override
    public void initialize() {
        myRobot = new MyRobot(hardwareMap, gamepad1, gamepad2);

        dt = new DriveTrain(myRobot);
        vs = new VertSlides(myRobot);
        is = new IntakeSlides(myRobot);

        vs_up = new VertSlidesUp(vs, myRobot);
        vs_down = new VertSlidesDown(myRobot);
        vs_run = new VertSlidesRun(vs);
        is_run = new IntakeSlidesRun(is);

        schedule(vs_up, vs_down);
        register(dt, vs, is);

        vs.setDefaultCommand(vs_run);
        is.setDefaultCommand(is_run);
    }
}
