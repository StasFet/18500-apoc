package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.arcrobotics.ftclib.controller.PIDFController;

import org.firstinspires.ftc.teamcode.core.MyRobot;

public class Lift extends SubsystemBase {

    private DcMotorEx motor1;
    private DcMotorEx motor2;

    double p = 1;
    double i = 0;
    double d = 0;
    double f = 0;

    PIDFController pidf = new PIDFController(0.001, 0, 0, 0);

    public Lift(MyRobot robot) {
        this.motor1 = robot.vslide_1;
        this.motor2 = robot.vslide_2;

        motor1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //encoder is used to be able to set velocity of motors
        //we should use a velocity pidf controller because we need precise control with low overshoot, and we dont need time to be super optimised
        motor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pidf.setTolerance(LIFT_TOLERANCE);
    }

    public void lift_up() {
        pidf.setSetPoint(LIFT_UP);
        wait_for_lift();
    }

    public void lift_down() {
        pidf.setSetPoint(LIFT_DOWN);
        wait_for_lift();
    }

    public void go_to_pos(int pos) {
        pidf.setSetPoint(pos);
        wait_for_lift();
    }

    private void wait_for_lift() {
        while(!pidf.atSetPoint()) {
            double output = pidf.calculate(motor1.getCurrentPosition());
            motor1.setVelocity(output);
            motor2.setVelocity(output);
        }

        motor1.setVelocity(0);
        motor2.setVelocity(0);
    }


}
