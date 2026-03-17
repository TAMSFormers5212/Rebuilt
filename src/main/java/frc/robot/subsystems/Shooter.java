package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final SparkMax motor;

    public Shooter() {
        motor = new SparkMax(20, MotorType.kBrushless);
        motor.configure(new SparkMaxConfig()
                .idleMode(SparkBaseConfig.IdleMode.kCoast)
                .inverted(false),
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
    }
}
