package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.hardware.rev.RevColorSensorV3;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.state.SubsystemStates;

@Config
public class Intake extends SubsystemBase {
    private MyRobot robot;
    private DcMotorEx intake;
    private DcMotorEx slides;
    private RevColorSensorV3 colorSensor;
    public ElapsedTime timer;

    public SubsystemStates.IntakeStates state;
    public IntakeColour colours;
    public boolean intakeBusy;

    double p = 1;
    double i = 0;
    double d = 0.1;
    double f = 0.0001;

    PIDFController pidf = new PIDFController(p, i, d, f);

    public Intake(MyRobot robot) {
        this.state = SubsystemStates.IntakeStates.IDLE;
        this.intakeBusy = false;
        this.robot = robot;
        this.intake = robot.intakeSpin;
        this.slides = robot.intakeSlide;
        this.colorSensor = robot.intakeColorSensor;
        this.colours = robot.intakeColour;
        this.timer = new ElapsedTime();

        pidf.setTolerance(INTAKE_TOLERANCE);
    }

    private void wait_for_slides() {
        while (!pidf.atSetPoint()) {
            double output = pidf.calculate(slides.getCurrentPosition());
            slides.setVelocity(output);
        }
    }

    public void contractSlides() {
        pidf.setSetPoint(INTAKE_IN_POS);
        wait_for_slides();
    }

    public void extendSlides() {
        pidf.setSetPoint(INTAKE_OUT_POS);
        wait_for_slides();
    }

    public void intakeOn() {
        intake.setPower(1);
    }

    public void intakeOff() {
        intake.setPower(0);
    }

    public void intakeEject() { intake.setPower(-1);}

    public void setState(SubsystemStates.IntakeStates state) {
        this.state = state;
        timer.reset();
    }

    public boolean checkSample() {
        boolean y = detectColour(Colours.YELLOW);
        boolean b = detectColour(Colours.BLUE);
        boolean r = detectColour(Colours.RED);
        switch (colours) {
            case YELLOW:
                return (y);
            case BLUE:
                return (b);
            case RED:
                return (r);
            case BLUEYELLOW:
                return (b || y);
            case REDYELLOW:
                return (r || y);
            default:
                return false;
        }
    }

    private boolean detectColour(Colours color) {
        int r = colorSensor.red();
        int g = colorSensor.green();
        int b = colorSensor.blue();
        switch (color) {
            case YELLOW: if (r > YELLOW_RED_MIN && g > YELLOW_GREEN_MIN) return true;
            case BLUE:   if (b > BLUE_MIN && g < OTHER_COLOUR_MAX && r < OTHER_COLOUR_MAX) return true;
            case RED:    if (r > RED_MIN && b < OTHER_COLOUR_MAX && g < OTHER_COLOUR_MAX) return true;
            default:     return false;
        }
    }

    enum Colours {
        RED,
        BLUE,
        YELLOW
    }

    public enum IntakeColour {
        RED,
        BLUE,
        REDYELLOW,
        BLUEYELLOW,
        YELLOW
    }
}
