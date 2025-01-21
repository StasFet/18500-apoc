package org.firstinspires.ftc.teamcode.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.commands.VertSlidesToggle;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.state.outtake.SlideStates;

public class VertSlides extends SubsystemBase {
    MyRobot robot;
    private final DcMotorEx vslide_1;
    private final DcMotorEx vslide_2;

    //pid controllers for the 2 motors
    private final PIDFController pid_s1 = new PIDFController(0, 0, 0, 0);
    private final PIDFController pid_s2 = new PIDFController(0, 0, 0, 0);

    public VertSlides(MyRobot robot_p) {
        robot = robot_p;
        vslide_1 = robot.vslide_1;
        vslide_2 = robot.vslide_2;
    }

    public void setSlidePositionByState(SlideStates state) {
        switch (state) {
            case UP:
                vslide_1.setPower(pid_s1.calculate(vslide_1.getCurrentPosition(), MyRobot.Constants.VSLIDES_UP));
                vslide_2.setPower(pid_s2.calculate(vslide_2.getCurrentPosition(), MyRobot.Constants.VSLIDES_UP));
                robot.state.slide_state = SlideStates.UP;       //failsafe
                break;
            case DOWN:
                vslide_1.setPower(pid_s1.calculate(vslide_1.getCurrentPosition(), MyRobot.Constants.VSLIDES_DOWN));
                vslide_2.setPower(pid_s2.calculate(vslide_2.getCurrentPosition(), MyRobot.Constants.VSLIDES_DOWN));
                robot.state.slide_state = SlideStates.DOWN;     //failsafe
                break;
        }
    }
}
