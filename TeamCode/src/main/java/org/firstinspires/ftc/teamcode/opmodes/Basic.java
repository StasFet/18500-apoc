package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;

@TeleOp(name = "Full Robot Control", group = "Linear Opmode")
@Config
public class Basic extends LinearOpMode {

    public static double servoPosition = 0.4;

    // Drivetrain motors
    DcMotor frontLeft, frontRight, backLeft, backRight;

    // Slide motors
    DcMotor hSlideMotor;
    DcMotor vSlideMotor1, vSlideMotor2;

    // Servos
    CRServo crServo1, crServo2;
    Servo regularServo;

    // Touch sensors
    TouchSensor hSlideTouch, vSlideTouch;

    @Override
    public void runOpMode() {
        // Hardware mapping
        frontLeft = hardwareMap.get(DcMotor.class, "fl");
        frontRight = hardwareMap.get(DcMotor.class, "fr");
        backLeft = hardwareMap.get(DcMotor.class, "bl");
        backRight = hardwareMap.get(DcMotor.class, "br");

        hSlideMotor = hardwareMap.get(DcMotor.class, "hslide");
        vSlideMotor1 = hardwareMap.get(DcMotor.class, "vslideLeft");
        vSlideMotor2 = hardwareMap.get(DcMotor.class, "vslideRight");

        crServo1 = hardwareMap.get(CRServo.class, "hangLeft");
        crServo2 = hardwareMap.get(CRServo.class, "hangRight");
        regularServo = hardwareMap.get(Servo.class, "PTO");

        hSlideTouch = hardwareMap.get(TouchSensor.class, "Hslide");
        vSlideTouch = hardwareMap.get(TouchSensor.class, "vslide");

        // Reverse motors as needed
        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);
        frontRight.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotorSimple.Direction.FORWARD);

        FtcDashboard dashboard = FtcDashboard.getInstance();

        waitForStart();

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            frontLeft.setPower(frontLeftPower);
            backLeft.setPower(backLeftPower);
            frontRight.setPower(frontRightPower);
            backRight.setPower(backRightPower);

            // --- CR Servos: X = spin right, B = spin left, Y = stop (Controller 1) ---
            if (gamepad1.x) {
                crServo1.setPower(0.2);
                crServo2.setPower(0.2);
            } else if (gamepad1.b) {
                crServo1.setPower(-0.2);
                crServo2.setPower(-0.2);
            } else if (gamepad1.y) {
                crServo1.setPower(0.0);
                crServo2.setPower(0.0);
            }

            // --- Horizontal Slide Control (Controller 2) ---
            if (gamepad2.a) {
                hSlideMotor.setPower(1.0);
            } else if (gamepad2.b) {
                hSlideMotor.setPower(-1.0);
            } else if (gamepad2.y) {
                hSlideMotor.setPower(0.0);
            }

            // --- Vertical Slide Control (Controller 2 Triggers) ---
            double liftPower = gamepad2.right_trigger - gamepad2.left_trigger;
            vSlideMotor1.setPower(liftPower);
            vSlideMotor2.setPower(-liftPower);

            regularServo.setPosition(servoPosition);

            // --- Touch Sensor Feedback ---
            telemetry.addData("Touch - hSlide:", hSlideTouch.isPressed() ? "PRESSED" : "Not Pressed");
            telemetry.addData("Touch - vSlide:", vSlideTouch.isPressed() ? "PRESSED" : "Not Pressed");

            telemetry.update();
        }
    }
}
