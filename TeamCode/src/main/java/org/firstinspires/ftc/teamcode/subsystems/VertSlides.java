package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;

public class VertSlides extends SubsystemBase {
    private final MyRobot robot;
    private final MyRobot.States robot_state;
    private final DcMotorEx vslide_1;
    private final DcMotorEx vslide_2;

    //pid controllers for the 2 motors
    private final PIDController pid_s1 = new PIDController(0, 0, 0);
    private final PIDController pid_s2 = new PIDController(0, 0, 0);

    public VertSlides(final MyRobot robot_p) {
        robot = robot_p;
        robot_state = robot.state;
        vslide_1 = robot.vslide_1;
        vslide_2 = robot.vslide_2;
    }

    public void run() {
        switch (robot_state.lift_state) {
            case UP_REST: case UP_ESCALATING: case UP_DEPOSITING:
                vslide_1.setPower(pid_s1.calculate(vslide_1.getCurrentPosition(), MyRobot.Constants.VSLIDES_UP));
                vslide_2.setPower(pid_s2.calculate(vslide_2.getCurrentPosition(), MyRobot.Constants.VSLIDES_UP));
                break;
            case DOWN_REST: case DOWN_LOWERING:
                vslide_1.setPower(pid_s1.calculate(vslide_1.getCurrentPosition(), MyRobot.Constants.VSLIDES_DOWN));
                vslide_2.setPower(pid_s2.calculate(vslide_2.getCurrentPosition(), MyRobot.Constants.VSLIDES_DOWN));
                break;
        }
    }
}
