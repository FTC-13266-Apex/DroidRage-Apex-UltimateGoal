package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.TrajectoryBuilder;
import com.acmerobotics.roadrunner.trajectory.constraints.AngularVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.MinVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.ProfileAccelerationConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TankVelocityConstraint;

import java.util.Arrays;
import java.util.Vector;

import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ACCEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_ANG_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.MAX_VEL;
import static org.firstinspires.ftc.teamcode.drive.DriveConstants.TRACK_WIDTH;
public class Trajectories {

    public static MinVelocityConstraint velConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
                new TankVelocityConstraint(MAX_VEL, TRACK_WIDTH)
        ));
    public static MinVelocityConstraint slowConstraint = new MinVelocityConstraint(Arrays.asList(
            new AngularVelocityConstraint(MAX_ANG_VEL),
            new TankVelocityConstraint(12, TRACK_WIDTH)
    ));
    public static ProfileAccelerationConstraint accelConstraint = new ProfileAccelerationConstraint(MAX_ACCEL);

    @Config
    public static class BlueLeftTape {
        public static Pose2d startPose = new Pose2d(-62.5, 52, Math.toRadians(180));

        public static double wobbleGoalX = 66;
        public static double wobbleGoalY = 40;

        public static double highGoalX = -3;
        public static double highGoalY = 23;
        public static double outtakeDistance = 36;
        public static double intakeDistance = 36;
        public static double intakeMoreDistance = 20;
        public static double wobbleDistance = 4;
        public static double wobbleAngle = 200;

        public static Trajectory driveToWobble = new TrajectoryBuilder(startPose, true, velConstraint, accelConstraint).splineTo(new Vector2d(wobbleGoalX, wobbleGoalY), Math.toRadians(0)).build();
        public static Trajectory wobbleToHighgoal = new TrajectoryBuilder(driveToWobble.end(), velConstraint, accelConstraint).splineTo(new Vector2d(highGoalX, highGoalY), Math.toRadians(180)).build();
        public static Trajectory highGoalHitIntake = new TrajectoryBuilder(wobbleToHighgoal.end(), velConstraint, accelConstraint).forward(outtakeDistance, velConstraint, accelConstraint).build();
        public static Trajectory intakeRings = new TrajectoryBuilder(highGoalHitIntake.end(), velConstraint, accelConstraint).forward(intakeDistance, velConstraint, accelConstraint).build();
        public static Trajectory intakeMoreRings = new TrajectoryBuilder(intakeRings.end(), velConstraint, accelConstraint).forward(intakeMoreDistance, velConstraint, accelConstraint).build();
        public static Trajectory ringsToWobble = new TrajectoryBuilder(intakeMoreRings.end().plus(new Pose2d(0, 0, Math.toRadians(wobbleAngle))), slowConstraint, accelConstraint).forward(wobbleDistance, slowConstraint, accelConstraint).build();



    }


}
