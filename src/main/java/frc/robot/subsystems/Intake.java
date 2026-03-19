package frc.robot.subsystems;

import frc.robot.Config;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkBase.ControlType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final SparkMax linkageMotorLeft;
    private final SparkMax linkageMotorRight;
    private final SparkMax intakeMotor;
    private double linkageStartingPosition = 0;
    private double linkagePosition = Config.linkagePosition;
    private boolean intakeRetracted = true;
    private boolean intakeRunning = false;

    private SparkClosedLoopController linkageController;

    public Intake() {
        linkageMotorLeft = new SparkMax(30, MotorType.kBrushless);
        linkageMotorLeft.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kBrake),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        linkageMotorRight = new SparkMax(31, MotorType.kBrushless);
        linkageMotorRight.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kBrake)
                .follow(linkageMotorRight, true),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);

        intakeMotor = new SparkMax(32, MotorType.kBrushless);
        intakeMotor.configure(new SparkMaxConfig()
                .idleMode(IdleMode.kBrake),
                ResetMode.kResetSafeParameters,
                PersistMode.kPersistParameters);  
        linkageStartingPosition = intakeMotor.getEncoder().getPosition();

        linkageController = linkageMotorLeft.getClosedLoopController();
    }

    public Command setIntakeSpeed(double speed)
    {
        return runOnce(() -> intakeMotor.set(speed));
    }

    public Command startIntake()
    {
        return runOnce(() -> setIntakeSpeed(1));
    }

    public Command stopIntake()
    {
        return runOnce(() -> setIntakeSpeed(0));
    }

    public boolean startedIntake() {
        return intakeMotor.get() != 0.0;
    }

    public double linkagePosition() {
        return intakeMotor.getEncoder().getPosition();
    }

    public Command setLinkagePosition(double position)
    {
        return runOnce(() -> linkageController.setSetpoint(linkagePosition, ControlType.kPosition));
    }

    public Command startLinkage()
    {
        return runOnce(() -> linkageMotorLeft.set(0.5));
    }

    public Command stopLinkage()
    {
        return runOnce(() -> linkageMotorLeft.set(0));
    }

    public Command toggleIntake()
    {
        intakeRunning = !intakeRunning;
        if(intakeRunning)
            return runOnce(() -> startIntake());
        else
            return runOnce(() -> stopIntake());
    }



    @Override
    public void periodic() {
        SmartDashboard.putNumber("intake speed", intakeMotor.get());
        SmartDashboard.putNumber("intake position", linkagePosition());
        SmartDashboard.putBoolean("isAt position", linkageController.isAtSetpoint());
    }
}
