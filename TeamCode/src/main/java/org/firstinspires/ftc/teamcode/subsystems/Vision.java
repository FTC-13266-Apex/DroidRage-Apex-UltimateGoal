package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.pipelines.CompHGPipeline;
import org.firstinspires.ftc.teamcode.pipelines.RingPipelineEx;
import org.firstinspires.ftc.teamcode.pipelines.UGBasicHighGoalPipeline;

import org.firstinspires.ftc.teamcode.Util;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvSwitchableWebcam;

import java.util.logging.Level;
@Config
public class Vision extends SubsystemBase {

    public static double TOP_PERCENT = 0.39;
    public static double BOTTOM_PERCENT = 0.52;
    public static double WIDTH_PERCENT = 0.9;
    OpenCvSwitchableWebcam switchableWebcam;
    WebcamName ringCamera, goalCamera;

    private Telemetry telemetry;
    private RingPipelineEx ringPipeline;
    private RingPipelineEx.Stack currentStack;

    private CompHGPipeline goalDetector;
    private UGBasicHighGoalPipeline.Mode color;

    private boolean runningRingDetector;

    public Vision(HardwareMap hw, String ringWebcam, String goalWebcam, Telemetry tl, double top, double bottom, double width, UGBasicHighGoalPipeline.Mode color) {
        ringCamera = hw.get(WebcamName.class, "webcam");
        goalCamera = hw.get(WebcamName.class, "webcam1");

        int cameraMonitorViewId = hw.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hw.appContext.getPackageName());

        ringPipeline = new RingPipelineEx();
        ringPipeline.setBottomRectangle(bottom, width);
        ringPipeline.setTopRectangle(top, width);
        ringPipeline.setRectangleSize(5, 5);

        goalDetector = new CompHGPipeline(color);

        switchableWebcam = OpenCvCameraFactory.getInstance().createSwitchableWebcam(cameraMonitorViewId, ringCamera, goalCamera);
        switchableWebcam.openCameraDevice();
        switchableWebcam.setActiveCamera(ringCamera);
        switchableWebcam.setPipeline(ringPipeline);

        runningRingDetector = true;
    }

    public void switchToHG() {
        if (isRunningHGDetector())
            return;

        switchableWebcam.setActiveCamera(goalCamera);
        switchableWebcam.setPipeline(goalDetector);
        runningRingDetector = false;
    }

    public void switchToStarter() {
        if (isRunningRingDetector())
            return;

        switchableWebcam.setActiveCamera(ringCamera);
        switchableWebcam.setPipeline(ringPipeline);
        runningRingDetector = true;
    }

    @Override
    public void periodic() {
        if (isRunningRingDetector()) {
            currentStack = ringPipeline.getStack();
            Util.logger(this, telemetry, Level.INFO, "Current Stack", currentStack);
            Util.logger(this, telemetry, Level.INFO, "Bottom", ringPipeline.getBottomAverage());
            Util.logger(this, telemetry, Level.INFO, "Top", ringPipeline.getTopAverage());
        }

        if (isRunningHGDetector()) {
            Util.logger(this, telemetry, Level.INFO, "Goal yaw (0 if not visible)", goalDetector.getTargetAngle());
            Util.logger(this, telemetry, Level.INFO, "Goal pitch (0 if not visible)", goalDetector.getTargetPitch());
        }
    }




    public void setBottomPercent(double bottomPercent, double width) { ringPipeline.setBottomRectangle(bottomPercent, width);
    }

    public void setTopPercent(double topPercent, double width) {
        ringPipeline.setBottomRectangle(topPercent, width);
    }
    public double getTopAverage() {
        return ringPipeline.getTopAverage();
    }
    public double getBottomAverage() {
        return ringPipeline.getTopAverage();
    }

    public double getHighGoalAngle() { return goalDetector.getTargetAngle(); }
    public boolean isTargetVisible() {
        return goalDetector.isTargetVisible();
    }
    public RingPipelineEx.Stack getCurrentStack() {
        return ringPipeline.getStack();
    }

    public boolean isRunningRingDetector() {
        return runningRingDetector;
    }

    public boolean isRunningHGDetector() {
        return !runningRingDetector;
    }

    public void setOffset(int offset) {
        goalDetector.setXOffset(offset);
    }
}
