package org.firstinspires.ftc.teamcode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.hardware.GyroEx;
import com.arcrobotics.ftclib.hardware.RevIMU;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.commands.DefaultDriveCommand;
import org.firstinspires.ftc.teamcode.commands.IntakeCommand;
import org.firstinspires.ftc.teamcode.commands.SlowDriveCommand;
import org.firstinspires.ftc.teamcode.subsystems.Drivetrain;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

@TeleOp(name="TeleOp")
public class Teleop extends CommandOpMode {

    private MotorEx leftBackDriveMotor, rightBackDriveMotor, leftFrontDriveMotor, rightFrontDriveMotor;
    private GyroEx gyro;

    private MotorEx intakeMotor;

    private Drivetrain m_drive;
    private GamepadEx m_driverOp;

    private DefaultDriveCommand m_driveCommand;
    private SlowDriveCommand slowDriveCommand;
    private Intake intake;

    private Button intakeButton, slowButton;

    @Override
    public void initialize() {
        leftBackDriveMotor = new MotorEx(hardwareMap, "rear_drive_left");
        leftFrontDriveMotor = new MotorEx(hardwareMap, "front_drive_left");
        rightBackDriveMotor = new MotorEx(hardwareMap, "rear_drive_right");
        rightFrontDriveMotor = new MotorEx(hardwareMap, "front_drive_right");

        gyro = new RevIMU(hardwareMap);


        m_drive = new Drivetrain(leftBackDriveMotor, rightBackDriveMotor, leftFrontDriveMotor, rightFrontDriveMotor, gyro);

        intakeMotor = new MotorEx(hardwareMap, "intake");
        intake = new Intake(intakeMotor);

        m_driverOp = new GamepadEx(gamepad1);

        m_driveCommand = new DefaultDriveCommand(m_drive, ()->m_driverOp.getLeftX(), ()->m_driverOp.getLeftY(), ()->m_driverOp.getRightX(), ()->m_driverOp.getRightY());
        
        intakeButton = (new GamepadButton(m_driverOp, GamepadKeys.Button.A))
                .whenPressed(new IntakeCommand(intake));
        slowButton = (new GamepadButton(m_driverOp, GamepadKeys.Button.LEFT_BUMPER))
                .whenPressed(new SlowDriveCommand(m_drive, ()->m_driverOp.getLeftX(), ()->m_driverOp.getLeftY(), ()->m_driverOp.getRightX(), ()->m_driverOp.getRightY()));


        m_drive.setDefaultCommand(m_driveCommand);
        register(m_drive, intake);
    }

}