package org.firstinspires.ftc.teamcode.cv;

import org.openftc.easyopencv.OpenCvPipeline;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class SampleCV extends OpenCvPipeline {

	// HSV Color Range for Detecting Yellow Samples
	public static final Scalar LOWER_YELLOW = new Scalar(20, 100, 100); // Adjust as needed
	public static final Scalar UPPER_YELLOW = new Scalar(30, 255, 255);

	// Debugging Mats
	private final Mat scaleDown = new Mat();
	private final Mat hsvMat = new Mat();
	private final Mat mask = new Mat();
	private final Mat hierarchy = new Mat();

	// Detected Angle
	private volatile double detectedAngle = 0.0;
	private volatile Point center = new Point(0, 0);

	@Override
	public Mat processFrame(Mat input) {
		Imgproc.resize(input, scaleDown, new Size(320, 240));
		// Step 1: Convert the image to HSV
		Imgproc.cvtColor(scaleDown, hsvMat, Imgproc.COLOR_RGB2HSV);

		// Step 2: Create a binary mask for yellow samples
		Core.inRange(hsvMat, LOWER_YELLOW, UPPER_YELLOW, mask);

		// Step 3: Find contours
		List<MatOfPoint> contours = new ArrayList<>();
		Imgproc.findContours(mask, contours, hierarchy, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		// Step 4: Identify the largest contour
		MatOfPoint largestContour = null;
		double maxArea = 0;

		for (MatOfPoint contour : contours) {
			double area = Imgproc.contourArea(contour);
			if (area > maxArea) {
				maxArea = area;
				largestContour = contour;
			}
		}

		// Step 5: Analyze the largest contour
		if (largestContour != null) {
			// Fit a rotated rectangle
			RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(largestContour.toArray()));
			center = rotatedRect.center;

			// Draw the rectangle on the frame for visualization
			Point[] rectPoints = new Point[4];
			rotatedRect.points(rectPoints);
			for (int i = 0; i < 4; i++) {
				Imgproc.line(input, rectPoints[i], rectPoints[(i + 1) % 4], new Scalar(0, 255, 0), 2);
			}

			// Calculate the angle of the rotated rectangle
			detectedAngle = rotatedRect.angle;

			// Normalize the angle for consistency
			if (rotatedRect.size.width < rotatedRect.size.height) {
				detectedAngle += 90;
			}

			// Display the angle on the screen
			Imgproc.putText(
					input,
					"Angle: " + String.format("%.2f", detectedAngle),
					new Point(10, 40),
					Imgproc.FONT_HERSHEY_SIMPLEX,
					1,
					new Scalar(255, 255, 255),
					2
			);
		} else {
			// If no contour is found, display a message
			Imgproc.putText(
					input,
					"No sample detected",
					new Point(10, 40),
					Imgproc.FONT_HERSHEY_SIMPLEX,
					1,
					new Scalar(255, 0, 0),
					2
			);
		}

		return input;
	}

	// Public method to retrieve the detected angle
	public double getDetectedAngle() {
		return detectedAngle;
	}

	public Point getCenter() {
		return center;
	}
}
