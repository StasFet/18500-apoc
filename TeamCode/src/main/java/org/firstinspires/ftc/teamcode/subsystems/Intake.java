package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.core.MyRobot;

@Config
public class Intake extends SubsystemBase {
    private MyRobot robot;
    private DcMotorEx intake;
    private DcMotorEx slides;

    double p = 1;
    double i = 0;
    double d = 0.1;
    double f = 0;

    PIDFController pidf = new PIDFController(p, i, d, f);

    public Intake(MyRobot robot) {
        this.robot = robot;
        this.intake = robot.intake_spin;
        this.slides = robot.intake_slide;

        pidf.setTolerance(INTAKE_TOLERANCE);
    }

    private void wait_for_slides() {
        while (!pidf.atSetPoint()) {
            double output = pidf.calculate(slides.getCurrentPosition());
            slides.setVelocity(output);
        }
    }

    private void contractSlides() {
        pidf.setSetPoint(INTAKE_IN_POS);
        wait_for_slides();
    }

    private void extendSlides() {
        pidf.setSetPoint(INTAKE_OUT_POS);
        wait_for_slides();
    }

    private void intakeOn() {
        intake.setPower(1);
    }

    private void intakeOff() {
        intake.setPower(0);
    }
}
