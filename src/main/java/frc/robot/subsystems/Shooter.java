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
    private final SparkMax motor;
    private final SparkMax motor2;

    public Shooter() {
        motor = new SparkMax(20, MotorType.kBrushless);
        motor.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kCoast)
                .inverted(false),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
        motor2 = new SparkMax(21, MotorType.kBrushless);
        motor2.configure(new SparkMaxConfig()
                .follow(motor, true)
                .idleMode(IdleMode.kCoast),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);
    }

    public boolean started() {
        return motor.get() != 0.0;
    }

    public Command start() {
        return start(1.0);
    }

    public Command start(double speed) {
        return runOnce(() -> motor.set(speed));
    }

    public Command stop() {
        return runOnce(() -> motor.set(0));
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("shooter speed", motor.get());
    }
}
