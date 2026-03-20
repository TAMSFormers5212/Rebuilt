package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final SparkMax shooterMotor;
    private final SparkMax transferMotor;

    public Shooter() {
        shooterMotor = new SparkMax(21, MotorType.kBrushless);
        shooterMotor.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kCoast)
                .inverted(false),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        transferMotor = new SparkMax(20, MotorType.kBrushless);
        transferMotor.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kCoast)
                .inverted(false),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public boolean startedShooter() {
        return shooterMotor.get() != 0.0;
    }

    public Command startShooter() {
        return startShooter(1.0);
    }

    public Command startShooter(double speed) {
        return runOnce(() -> shooterMotor.set(speed));
    }

    public Command stopShooter() {
        return runOnce(() -> shooterMotor.set(0));
    }

    public boolean startedTransfer() {
        return transferMotor.get() != 0.0;
    }

    public Command startTransfer() {
        return startTransfer(1.0);
    }

    public Command startTransfer(double speed) {
        return runOnce(() -> transferMotor.set(speed));
    }

    public Command stopTransfer() {
        return runOnce(() -> transferMotor.set(0));
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("shooter speed", shooterMotor.get());
        SmartDashboard.putNumber("transfer speed", transferMotor.get());
    }
}
