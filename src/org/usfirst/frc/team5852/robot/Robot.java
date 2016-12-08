package org.usfirst.frc.team5852.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


// class -> (not using)robotinit -> autoinit -> autoPeriodic -> teleopinit -> telopPeriodic

public class Robot extends IterativeRobot {
	//Command autocommand;
	private SendableChooser chooser;

	//final String defaultAuto = "Default";

	//final String tripleR = "tripleR";

	String autoSelected;


	// Drive Speed Controllers

	VictorSP frontLeft = new VictorSP(0);

	VictorSP rearLeft = new VictorSP(1);

	VictorSP frontRight = new VictorSP(2);

	VictorSP rearRight = new VictorSP(3);

	// Lift speed controllers

	Talon lift = new Talon(4);

	Talon intake = new Talon(6);

	// DriveTrain

	RobotDrive drive = new RobotDrive(frontLeft, rearLeft, frontRight, rearRight);

	// Joysticks

	Joystick flightstick = new Joystick(0);

	Joystick logCon = new Joystick(1);

	// Buttons for Joysticks

	int Yaxis = 1;
	int Xaxis = 0;
	int buttonA = 2;
	int buttonY = 4;
	int buttonX = 1;
	int buttonB = 3;
	
	private int mode = 1;
	public void robotInit() 
	{

		chooser = new SendableChooser();
		chooser.addDefault("No Auto", 1);
		chooser.addObject("tripleR", 2);
		chooser.addObject("lowbar", 3);
		SmartDashboard.putData("Auto choices", chooser);
	}

	public void autonomousInit() 
	{

		mode = (int) chooser.getSelected();
		//autocommand.start();

		// autoSelected = SmartDashboard.getString("Auto Selector",

		// defaultAuto);

		System.out.println("Auto selected: " + autoSelected);

	}

	public void autonomousPeriodic() 
	{
		Scheduler.getInstance().run();
		if (mode == 1)
		{
			//No Autonomous
			while (isAutonomous() && isEnabled())
			{
				
				break;
				
			}
			
		}else if (mode == 2)
		{
			
			//Ramparts, Rock Wall, and Rough Terrain Autonomous
			while (isAutonomous() && isEnabled())
			{
				while (Timer.getMatchTime() < 3)
				{
					drive.tankDrive(0.5, 0.5);
				}
				Timer.delay(2);
				while (Timer.getMatchTime() > 5 && Timer.getMatchTime() < 10)
				{
					drive.tankDrive(1, 1);
				}
				Timer.delay(5);
				/*
				//Approaches Obstacle at Half Speed
				for (int i = 0; i < 80000; i++) 
				{
					drive.tankDrive(0.5, 0.5);
				}
				//Guns It Over The Obstacle
				Timer.delay(2);
				for (int i = 0; i < 200000; i++)
				{
					drive.tankDrive(1, 1);
				}
				//Delays the Autonomous for the Rest of the Autonomous Period and Breaks the Loop
				Timer.delay(13);*/
				
				break;
			}
		}else if (mode == 3)
		{
			//Low Bar Autonomous
			while (isAutonomous() && isEnabled())
			{
				//Lowers the Intake
				for (int i = 0; i < 40; i++)
				{ 
					//try changing the speed of the lift motor
					lift.set(-0.5);
				}
				Timer.delay(2);
				//Drives Forward at Half Speed
				for (int i = 0; i < 70000; i++)
				{
					drive.tankDrive(0.5, 0.5);
				}
				Timer.delay(2);
				//Drives Forward at Half Speed (Again)
				for (int i = 0; i < 100000; i++)
				{
					drive.tankDrive(0.5, 0.5);
				}
				//Delays the Autonomous for the Rest of the Autonomous Period and Breaks the Loop
				Timer.delay(13);
				break;	
			}
		}		
	}

	public void teleopInit() 
	{

	}

	public void teleopPeriodic() 
	{
		
		while (isOperatorControl() && isEnabled()) 
		{
			//Identifies the Drive Train Using the Flightstick
			drive.arcadeDrive(flightstick.getY(), -flightstick.getX());

			//Timer.delay(0.1);

			//Intake Commands: buttonY Intakes the Ball In, buttonA Outakes the Ball
			if (logCon.getRawButton(buttonY))
			{
				//Sets the Intake Motor to Intake at Full Speed
				intake.set(1);

			}else if (logCon.getRawButton(buttonA))
			{
				//Sets the Intake Motor to Outake at Full Speed
				intake.set(-1);

			}else
			{
				//Sets the Intake Motor to Stop
				intake.set(0);
			}
			//Lift Commands: buttonX Lowers the Lift, buttonB Raises the Lift
			if (logCon.getRawButton(buttonX))
			{
				//Lowers the Intake at 75%
				lift.set(-0.75);

			}else if (logCon.getRawButton(buttonB))
			{
				//Raises the Intake at 75%
				lift.set(0.75);

			}else
			{
				//Sets the Lift to Stop
				lift.set(0);
			}

		}

	}


	public void testPeriodic() 
	{

	}

}