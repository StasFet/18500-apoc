package org.firstinspires.ftc.teamcode.opmodes;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.Robot;
import org.firstinspires.ftc.teamcode.core.MyRobot;
import org.firstinspires.ftc.teamcode.subsystems.DriveTrain;

public class OpMode extends CommandOpMode {
    @Override
    public void initialize() {
        MyRobot myRobot = new MyRobot(hardwareMap, gamepad1, gamepad2);

        DriveTrain dt = new DriveTrain(hardwareMap, myRobot);
    }
}
