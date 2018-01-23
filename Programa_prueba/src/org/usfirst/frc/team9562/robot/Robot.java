/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team9562.robot;


import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team9562.robot.commands.ExampleCommand;
import org.usfirst.frc.team9562.robot.subsystems.ExampleSubsystem;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static final ExampleSubsystem kExampleSubsystem
			= new ExampleSubsystem();
	public static OI m_oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();
	
	
	
	ADXRS450_Gyro gyro;
	double angulo;
	Joystick j_izquierdo,j_derecho;
	Button b_x,b_y,b_a,b_b;
	BuiltInAccelerometer acelerometro;
	
	DifferentialDrive transmision;
	
	PWMTalonSRX m_izquierdo1,m_izquierdo2,m_derecho1,m_derecho2;
	
	SpeedControllerGroup izquierdo,derecho;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
		gyro = new ADXRS450_Gyro();
		j_izquierdo= new Joystick(0);
		j_derecho= new Joystick(1);
		
		b_x= new JoystickButton(j_derecho, 0);
		b_y= new JoystickButton(j_derecho, 1);
		b_a= new JoystickButton(j_derecho, 2);
		b_b= new JoystickButton(j_derecho, 3);

		acelerometro= new BuiltInAccelerometer (Accelerometer.Range.k16G);
		
		izquierdo = new SpeedControllerGroup(m_izquierdo1,m_izquierdo2);
		derecho = new SpeedControllerGroup(m_derecho1,m_derecho2);
		
		
		m_izquierdo1 = new PWMTalonSRX(0);
		m_izquierdo2 = new PWMTalonSRX(1);
		m_derecho1 = new PWMTalonSRX(2);
		m_derecho2 = new PWMTalonSRX(3);
		
		transmision= new DifferentialDrive(izquierdo, derecho);
		
		
		
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		transmision.tankDrive(j_izquierdo.getY(),j_derecho.getY());
		
		
		angulo= gyro.getAngle();
		
		
		System.out.println(angulo);
		
		if(b_x.get()) System.out.println("X presionado");
		
		if(b_y.get()) System.out.println("Y presionado");

		if(b_b.get()) System.out.println("B presionado");
		
		if(b_a.get()) System.out.println("A presionado");
		
		System.out.println("X"+acelerometro.getX());
		
		System.out.println("Y"+acelerometro.getY());
		
		System.out.println("Z"+acelerometro.getZ());
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
