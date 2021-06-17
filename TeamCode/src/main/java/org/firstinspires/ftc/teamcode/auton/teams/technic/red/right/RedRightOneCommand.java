package org.firstinspires.ftc.teamcode.auton.teams.technic.red.right;

import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.auton.values.outside.OutsideZeroValues;
import org.firstinspires.ftc.teamcode.commands.PlaceWobbleGoal;
import org.firstinspires.ftc.teamcode.commands.drive.DriveForwardCommand;
import org.firstinspires.ftc.teamcode.commands.drive.TurnCommand;
import org.firstinspires.ftc.teamcode.commands.drive.TurnToCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.ShooterFeeder;
import org.firstinspires.ftc.teamcode.subsystems.ShooterWheels;
import org.firstinspires.ftc.teamcode.subsystems.Vision;
import org.firstinspires.ftc.teamcode.subsystems.WobbleGoalArm;

import org.firstinspires.ftc.teamcode.auton.values.outside.OutsideOneValues;

public class RedRightOneCommand extends SequentialCommandGroup {
    public RedRightOneCommand(Drivetrain drivetrain, ShooterWheels shooterWheels, ShooterFeeder feeder, Intake intake, WobbleGoalArm wobbleGoalArm, Vision vision, Telemetry telemetry) {
        final int HG_SPEED = 3600;
        final int POWERSHOT_SPEED = 3000;

        OutsideOneValues distance = new OutsideOneValues();
        OutsideOneValues angle = new OutsideOneValues();

        addCommands(
                new RedRightShootingSequence(drivetrain, shooterWheels, feeder),

                new DriveForwardCommand(drivetrain, distance.distanceOne),
                new TurnToCommand(drivetrain, angle.angleOne, true),
                new DriveForwardCommand(drivetrain, distance.distanceTwo),
                new PlaceWobbleGoal(wobbleGoalArm),
                new DriveForwardCommand(drivetrain, distance.distanceThree),
                new TurnToCommand(drivetrain, angle.angleTwo, true),
                new DriveForwardCommand(drivetrain, distance.distanceFour)

                );
    }
}
    //new SplineCommand(drivetrain, new Vector2d(-42, 12), Math.toRadians(180)),
    //new DriveForwardCommand(drivetrain, -40),
    //new SplineCommand(drivetrain, new Vector2d(-30, 24), Math.toRadians(15), false),