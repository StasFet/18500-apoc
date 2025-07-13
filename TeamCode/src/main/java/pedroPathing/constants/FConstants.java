package pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.localization.Localizers;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.pedropathing.localization.Localizers;
//import com.pedropathing.follower.FollowerConstants;
//import com.pedropathing.util.CustomFilteredPIDFCoefficients;
//import com.pedropathing.util.CustomPIDFCoefficients;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static org.firstinspires.ftc.teamcode.core.Constants.*;

@Config
public class FConstants {
    static {
        FollowerConstants.localizers = Localizers.PINPOINT;

        FollowerConstants.leftFrontMotorName = NAME_FL;
        FollowerConstants.leftRearMotorName = NAME_BL;
        FollowerConstants.rightFrontMotorName = NAME_FR;
        FollowerConstants.rightRearMotorName = NAME_BR;

        FollowerConstants.leftFrontMotorDirection = DcMotorSimple.Direction.REVERSE;
        FollowerConstants.leftRearMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightFrontMotorDirection = DcMotorSimple.Direction.FORWARD;
        FollowerConstants.rightRearMotorDirection = DcMotorSimple.Direction.FORWARD;

//        FollowerConstants.leftFrontMotorMulti = 1.0;
//        FollowerConstants.leftRearMotorMulti = 1.0;
//        FollowerConstants.rightFrontMotorMulti = 1.0;
//        FollowerConstants.rightRearMotorMulti = 1.0;

        FollowerConstants.mass = 14.9;

        //34.568
        FollowerConstants.xMovement = 60.52930269485795;
        FollowerConstants.yMovement = 41.37305417904997;   //50.412

        FollowerConstants.forwardZeroPowerAcceleration = -30.36955620458213;
        FollowerConstants.lateralZeroPowerAcceleration = -80.61373962204883;

        FollowerConstants.translationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0);
        FollowerConstants.useSecondaryTranslationalPID = false;
        FollowerConstants.secondaryTranslationalPIDFCoefficients.setCoefficients(0.1,0,0.01,0); // Not being used, @see useSecondaryTranslationalPID

        FollowerConstants.headingPIDFCoefficients.setCoefficients(2,0,0.1,0);
        FollowerConstants.useSecondaryHeadingPID = false;
        FollowerConstants.secondaryHeadingPIDFCoefficients.setCoefficients(2,0,0.1,0); // Not being used, @see useSecondaryHeadingPID

        FollowerConstants.drivePIDFCoefficients.setCoefficients(0.03,0,0.000005,0.6,0);
        FollowerConstants.useSecondaryDrivePID = false;
        FollowerConstants.secondaryDrivePIDFCoefficients.setCoefficients(0.1,0,0,0.6,0); // Not being used, @see useSecondaryDrivePID

        FollowerConstants.zeroPowerAccelerationMultiplier = 1;
        FollowerConstants.centripetalScaling = 0.0005;

        FollowerConstants.pathEndTimeoutConstraint = 500;
        FollowerConstants.pathEndTValueConstraint = 0.995;
        FollowerConstants.pathEndVelocityConstraint = 0.1;
        FollowerConstants.pathEndTranslationalConstraint = 0.1;
        FollowerConstants.pathEndHeadingConstraint = 0.007;
    }
}
