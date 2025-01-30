package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

public class MechDriveTrain {

    public DcMotorEx frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor;
    private double mmPerTick = 0.401;
    private IMU imu;
    private ElapsedTime timer = new ElapsedTime();
    private double CT = 0;
    public void init(HardwareMap hard){

        frontLeftMotor = (DcMotorEx) hard.dcMotor.get("frontLeftMotor");
        backLeftMotor = (DcMotorEx) hard.dcMotor.get("backLeftMotor");
        frontRightMotor = (DcMotorEx) hard.dcMotor.get("frontRightMotor");
        backRightMotor = (DcMotorEx) hard.dcMotor.get("backRightMotor");

        frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        // Reverse the right side motors. This may be wrong for your setup.
        // If your robot moves backwards when commanded to go forwards,
        // reverse the left side instead.
        // See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // Retrieve the IMU from the hardware map

        imu = hard.get(IMU.class, "imu");
        // Adjust the orientation parameters to match your robot
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.LEFT,
                RevHubOrientationOnRobot.UsbFacingDirection.DOWN));
        // Without this, the REV Hub's orientation is assumed to be logo up / USB forward
        imu.initialize(parameters);


    }
    public void OpMode(Gamepad g1){
        double y = -g1.left_stick_y;
        double x = g1.left_stick_x * 1.1;
        double rx = -g1.right_stick_x;
        if (g1.y) {
            imu.resetYaw();
        }

        double botHeading = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);

        // Rotate the movement direction counter to the bot's rotation
        double rotX = x * Math.cos(-botHeading) - y * Math.sin(-botHeading);
        double rotY = x * Math.sin(-botHeading) + y * Math.cos(-botHeading);

        rotX = rotX * 1.1;  // Counteract imperfect strafing

        // Denominator is the largest motor power (absolute value) or 1
        // This ensures all the powers maintain the same ratio,
        // but only if at least one is out of the range [-1, 1]
        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frontLeftPower = (rotY + rotX + rx) / denominator;
        double backLeftPower = (rotY - rotX + rx) / denominator;
        double frontRightPower = (rotY - rotX - rx) / denominator;
        double backRightPower = (rotY + rotX - rx) / denominator;


        frontLeftMotor.setPower(frontLeftPower*0.75);
        backLeftMotor.setPower(backLeftPower*0.75);
        frontRightMotor.setPower(frontRightPower*0.75);
        backRightMotor.setPower(backRightPower*0.75);
    }

    public void Forward(double speed, double time){
        CT = timer.milliseconds();
        while (timer.milliseconds()-CT<time){

            frontLeftMotor.setVelocity(speed);
            frontRightMotor.setVelocity(speed);
            backLeftMotor.setVelocity(speed);
            backRightMotor.setVelocity(speed);

        }
        frontLeftMotor.setVelocity(0);
        frontRightMotor.setVelocity(0);
        backLeftMotor.setVelocity(0);
        backRightMotor.setVelocity(0);

    }
    public void Back(double speed, double time){
        CT = timer.milliseconds();
        while (timer.milliseconds()-CT<time){

            frontLeftMotor.setVelocity(-speed);
            frontRightMotor.setVelocity(-speed);
            backLeftMotor.setVelocity(-speed);
            backRightMotor.setVelocity(-speed);

        }
        frontLeftMotor.setVelocity(0);
        frontRightMotor.setVelocity(0);
        backLeftMotor.setVelocity(0);
        backRightMotor.setVelocity(0);

    }
    public void Right(double speed, double time){
        CT = timer.milliseconds();
        while (timer.milliseconds()-CT<time){

            frontLeftMotor.setVelocity(speed);
            frontRightMotor.setVelocity(-speed);
            backLeftMotor.setVelocity(-speed);
            backRightMotor.setVelocity(speed);

        }
        frontLeftMotor.setVelocity(0);
        frontRightMotor.setVelocity(0);
        backLeftMotor.setVelocity(0);
        backRightMotor.setVelocity(0);

    }
    public void Left(double speed, double time){
        CT = timer.milliseconds();
        while (timer.milliseconds()-CT<time){

            frontLeftMotor.setVelocity(-speed);
            frontRightMotor.setVelocity(speed);
            backLeftMotor.setVelocity(speed);
            backRightMotor.setVelocity(-speed);

        }
        frontLeftMotor.setVelocity(0);
        frontRightMotor.setVelocity(0);
        backLeftMotor.setVelocity(0);
        backRightMotor.setVelocity(0);

    }

}
