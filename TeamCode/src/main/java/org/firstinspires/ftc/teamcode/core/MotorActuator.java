package org.firstinspires.ftc.teamcode.core;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.util.MathUtils;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MotorActuator {
	private List<DcMotorEx> motorList = new ArrayList<>();
	private PIDController controller;
	private double feedforward = 0.0;

	private double currentPosition = 0.0;
	private double power = 0.0;
	private double previousPower = 0.0;
	private double targetPosition = 0.0;
	private double tolerance = 0.0;
	private double limit = 0.0;

	private double maxPower = 1;

	private boolean reached = false;

	private Supplier<Object> topic;

	public MotorActuator(DcMotorEx ... motors) {
		for (DcMotorEx m : motors) {
			this.motorList.add(m);
		}
		read();
	}

	public MotorActuator(Supplier<Object> topic, DcMotorEx... motors) {
		for (DcMotorEx m : motors) {
			this.motorList.add(m);
		}
		this.topic = topic;
		read();
	}

	public void read() {
		if (topic != null) {
			Object value = topic.get();
			if (value instanceof Integer) {
				this.currentPosition = (int) value;
				return;
			} else if (value instanceof Double) {
				this.currentPosition = (double) value;
				return;
			}
		}
		this.currentPosition = 0.0;
	}

	public void periodic(double manualPower) { //TELEOP
		this.power = manualPower;
	}

	public void periodic() {
		if (controller != null) {
			this.power = controller.calculate(currentPosition, targetPosition);
			this.power += feedforward;
			this.power = MathUtils.clamp(power, -1, 1);
		}
//        if (Globals.IS_AUTO) {
//            double correction = 12/ RobotHardware.getInstance().getInitVoltage();
//            power *= correction;
//        }

		this.reached = Math.abs(targetPosition - currentPosition) <= tolerance;
	}

	public void write() {
		if (Math.abs(power - previousPower) > 0.005) {
			for (DcMotorEx m : motorList) {
				m.setPower(power);
				previousPower = power;
			}
		}
	}

	public MotorActuator setPIDController(PIDController controller) {
		this.controller = controller;
		return this;
	}

	public MotorActuator setFeedfoward(double feedfoward) {
		this.feedforward = feedfoward;
		return this;
	}

	public MotorActuator setCurrentPosition(int position) {
		this.currentPosition = position;
		return this;
	}

	public MotorActuator setErrorTolerance(int tolerance) {
		this.tolerance = tolerance;
		return this;
	}

	public MotorActuator setLimit(int limit) {
		this.limit = limit;
		return this;
	}

	public MotorActuator setMaxPower(double maxPower) {
		this.maxPower = maxPower;
		return this;
	}

	public void setTargetPosition(double targetPosition) {
		this.targetPosition = Math.min(targetPosition, limit);
	}

	public void updatePIDCoefficients(double p, double i, double d) {
		this.controller.setPID(p, 0, d);
	}

	public boolean hasReached() {return this.reached;}

	public boolean canTransfer() { return currentPosition <= 10; }

	public double getPosition() {
		return currentPosition;
	}

	public double getPower() {
		return this.previousPower;
	}

	public double getTargetPosition() {
		return this.targetPosition;
	}
}
