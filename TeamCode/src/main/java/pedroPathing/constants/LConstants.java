package pedroPathing.constants;

import com.acmerobotics.dashboard.config.Config;
import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

@Config
public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = -.0031;
        ThreeWheelConstants.strafeTicksToInches = .0031;
        ThreeWheelConstants.turnTicksToInches = -0.0267;
        ThreeWheelConstants.leftY = 4.25;
        ThreeWheelConstants.rightY = -4.25;
        ThreeWheelConstants.strafeX = -5;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "rightRear";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "rightFront";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "leftRear";
        ThreeWheelConstants.leftEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.rightEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;

        //stuff for us to use - pinpoint odometry constants from pedropathing website
//        PinpointConstants.forwardY = 1;
//        PinpointConstants.strafeX = -2.5;
//        PinpointConstants.distanceUnit = DistanceUnit.INCH;
//        PinpointConstants.hardwareMapName = "pinpoint";
//        PinpointConstants.useYawScalar = false;
//        PinpointConstants.yawScalar = 1.0;
//        PinpointConstants.useCustomEncoderResolution = false;
//        PinpointConstants.encoderResolution = GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD;
//        PinpointConstants.customEncoderResolution = 13.26291192;
//        PinpointConstants.forwardEncoderDirection = GoBildaPinpointDriver.EncoderDirection.REVERSED;
//        PinpointConstants.strafeEncoderDirection = GoBildaPinpointDriver.EncoderDirection.FORWARD;
    }
}




