// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.commands.Autos;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Robot extends TimedRobot {
    private Command m_autonomousCommand;

    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
    private final Drive drive = new Drive();
    private final Shooter shooter = new Shooter();

    private final CommandXboxController driverController = new CommandXboxController(0);
    private final CommandXboxController operatorController = new CommandXboxController(1);

    public Robot() {
        // new Trigger(m_exampleSubsystem::exampleCondition)
        // .onTrue(new ExampleCommand(m_exampleSubsystem));
        operatorController.a().onTrue(shooter.start());
        operatorController.b().onTrue(shooter.stop());
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
        if (m_autonomousCommand != null) {
            m_autonomousCommand.cancel();
        }
    }

    @Override
    public void teleopPeriodic() {
        var x = -driverController.getLeftY();
        var y = -driverController.getLeftX();
        var r = driverController.getRightX();
        drive.drive(x, y, r);
        SmartDashboard.putString("drive(x, y, r)", String.format("(%4.1f, %4.1f, %4.1f)", x, y, r));
    }

    @Override
    public void autonomousInit() {
        m_autonomousCommand = getAutonomousCommand();

        if (m_autonomousCommand != null) {
            m_autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void disabledInit() {
        CommandScheduler.getInstance().schedule(shooter.stop());
    }

    @Override
    public void disabledPeriodic() {
    }

    public Command getAutonomousCommand() {
        return Autos.exampleAuto(m_exampleSubsystem);
    }
}
