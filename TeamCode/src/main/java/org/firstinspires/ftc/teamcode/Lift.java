package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
@Config
public class Lift {
    public Servo grabServo, liftServo;
    public DcMotorEx leftLiftMotor, rightLiftMotor;
    private int maxheight = 1490;
    public  double normalOperationValue = 0.2, currentOperationValue = 0.2;
    private double highLoadOperationValue = 1;
    private double grabPressTime, liftPressTime, amplifierPressTime = 0;
    public static double grabServoClosedPos = 1;
    public static double        grabServoOpenedPos = 0.85;
    public static double        liftServoClosedPos = 0.45;
    public static double        liftServoOpenedPos = 0.135;
    private int lastLeftPos, lastRightPos = 0;
    private boolean isStopped = false;
    private boolean isGrabClosed, isLiftClosed = true;
    private ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hard){
        grabServo = hard.get(Servo.class, "grabServo");
        liftServo = hard.get(Servo.class, "liftServo");

        grabServo.setPosition(grabServoClosedPos);
        liftServo.setPosition(0.55);

        grabServo.setDirection(Servo.Direction.FORWARD);
        leftLiftMotor = (DcMotorEx) hard.dcMotor.get("leftLiftMotor");
        rightLiftMotor = (DcMotorEx) hard.dcMotor.get("rightLiftMotor");
        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);





    }
    public void StickMover(Gamepad g2){
        if (g2.b & timer.milliseconds()-liftPressTime>250 & isLiftClosed){
            liftServo.setPosition(liftServoOpenedPos);
            isLiftClosed = false;
            liftPressTime = timer.milliseconds();
        } else if (g2.b & (timer.milliseconds()-liftPressTime>250) & !isLiftClosed) {
            liftServo.setPosition(liftServoClosedPos);
            isLiftClosed = true;
            liftPressTime = timer.milliseconds();

        }
    }
    public void GrabAction(Gamepad g2){
        if (g2.a & timer.milliseconds()-grabPressTime>250 & isGrabClosed){
            grabServo.setPosition(grabServoOpenedPos);
            isGrabClosed = false;
            grabPressTime = timer.milliseconds();
        } else if (g2.a & timer.milliseconds()-grabPressTime>250 & !isGrabClosed) {
            grabServo.setPosition(grabServoClosedPos);
            isGrabClosed = true;
            grabPressTime = timer.milliseconds();

        }


    }
    public void LiftAction(Gamepad g2){
            if (g2.y & currentOperationValue!=highLoadOperationValue & timer.milliseconds()-amplifierPressTime>250){
                currentOperationValue = highLoadOperationValue;
                amplifierPressTime = timer.milliseconds();
            }
            else if (g2.y & currentOperationValue!=normalOperationValue & timer.milliseconds()-amplifierPressTime>250){
                currentOperationValue = normalOperationValue;
                amplifierPressTime = timer.milliseconds();
            }

            if (g2.dpad_up & (leftLiftMotor.getCurrentPosition()<maxheight & rightLiftMotor.getCurrentPosition()<maxheight)) {
                isStopped = false;
                leftLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                leftLiftMotor.setVelocity(1000);
                rightLiftMotor.setVelocity(1000);

                lastLeftPos = leftLiftMotor.getCurrentPosition();
                lastRightPos = rightLiftMotor.getCurrentPosition();
            } else if (g2.dpad_down & (leftLiftMotor.getCurrentPosition()>-5 & rightLiftMotor.getCurrentPosition()>-5)) {
                isStopped = false;
                leftLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                rightLiftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                leftLiftMotor.setVelocity(-1000);
                rightLiftMotor.setVelocity(-1000);

                lastLeftPos = leftLiftMotor.getCurrentPosition();
                lastRightPos = rightLiftMotor.getCurrentPosition();

            } else {
                if (!isStopped) {
                    leftLiftMotor.setVelocity(5);
                    rightLiftMotor.setVelocity(5);
                    isStopped = true;
                }
                leftLiftMotor.setTargetPosition(lastLeftPos);
                rightLiftMotor.setTargetPosition(lastRightPos);

                leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                leftLiftMotor.setPower(currentOperationValue);
                rightLiftMotor.setPower(currentOperationValue);
            }
        }

    }

