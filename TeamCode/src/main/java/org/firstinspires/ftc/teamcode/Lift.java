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
    public Servo grabServo, liftServo, rotationServo, rotationrotationServo;
    public DcMotorEx leftLiftMotor, rightLiftMotor;
    //максимальная позиция поворота захвата
    public static double rotmaxpos = 0.78;
    //средняя позиция поворота захвата
    public static double rotmidpos = 0.42;
    //Переменная для указания текущей позиции (по умолчанию в ней указана позиция поворота в которой робот стартует)
    public double currentRotPos = rotmidpos;
    //минимальная позиция поворота захвата
    public static double rotminpos = 0.075;
    //начальная позиция поворота захвата
    public static double rotrotStartingpos = 0.1;
    //позиция поворота для сбора проб со стенки
    public static double rotrotAimingpos = 0.807;
    //позиция поворота для подвеса проб на перекладину
    public static double rotrotFishingpos = 0.47;
    //позиция для захвата проб с пола
    public static double rotrotScoringingpos = 0.42;
    //указываем максимальную высоту подъемника
    private int maxheight = 1200;
    public  double normalOperationValue = 0.2, currentOperationValue = 0.2;
    private double highLoadOperationValue = 1;
    private double grabPressTime, liftPressTime, amplifierPressTime, rotTimer, sequenceTimer = 0;
    //позиция поворота сервопривода для закрытия клешней захвата
    public static double grabServoClosedPos = 0.85;
    //позиция поворота сервопривода для открытых клешней захвата
    public static double        grabServoOpenedPos = 0.62;
    //позиция подъемника когда он опущен
    public static double        liftServoClosedPos = 0.66;
    //позиция подъемника когда он поднят
    public static double        liftServoOpenedPos = 0.18;
    //переменные для запоминания прошлых позиций
    private int lastLeftPos, lastRightPos = 0;
    //переменная для указания позиций захвата (изменяется при нажатии клавиш на геймпаде)
    private int currentSequence = -1;
    //переменная для проверки должна ли изменится позиция захвата
    private boolean isStopped, isSequenceRunning = false;
    //переменные для проверки открыта ли клешня захвата и опущен ли подъемник
    private boolean isGrabClosed, isLiftClosed = true;
    private ElapsedTime timer = new ElapsedTime();

    public void init(HardwareMap hard){
        //указание сервопривода для закрытия и открытия клешней захвата
        grabServo = hard.get(Servo.class, "grabServo");
        //указание сервопривода для поднятия подъемника
        liftServo = hard.get(Servo.class, "liftServo");
        //указание сервопривода для поворота клешней захвата
        rotationServo = hard.get(Servo.class, "rotationServo");
        //указание сервопривода для поворота сервопривода для поворота клешней захвата
        rotationrotationServo = hard.get(Servo.class, "rotationrotationServo");
        // указания в какую сторону должны вращаться сервоприводы

        grabServo.setDirection(Servo.Direction.FORWARD);
        liftServo.setDirection(Servo.Direction.REVERSE);

        InitSeq();

        //обозначение моторов подъемника
        leftLiftMotor = (DcMotorEx) hard.dcMotor.get("leftLiftMotor");
        rightLiftMotor = (DcMotorEx) hard.dcMotor.get("rightLiftMotor");
        //указание в какую сторону должен вращаться мотор
        rightLiftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        //указываем режим движения моторов с помощью энкодеров
        leftLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightLiftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        rightLiftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);





    }
    // перемещение захвата в автономе
    public void LiftGoToAUTO(int ticks){
        leftLiftMotor.setTargetPosition(ticks);
        rightLiftMotor.setTargetPosition(ticks);

        leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftLiftMotor.setPower(0.4);
        rightLiftMotor.setPower(0.4);
    }
    public void LiftGoToOP(int ticks){
        lastLeftPos = ticks;
        lastRightPos = ticks;

        currentOperationValue = 0.6;
    }
    //класс для считывания изменений позиций захвата с геймпада
    public void SequenceSwitch(Gamepad g2){
        if (g2.left_stick_button) {
            sequenceTimer = timer.milliseconds();

            currentSequence = 1;
        } else if (g2.x) {
            sequenceTimer = timer.milliseconds();

            currentSequence = 2;

        } else if (g2.right_stick_button) {
            sequenceTimer = timer.milliseconds();

            currentSequence = 3;

        }


    }
    //класс для перемещения захвата в начальную позицию
    public void InitSeq(){
        if (currentSequence==-1) {

            rotationServo.setPosition(rotmidpos);
            grabServo.setPosition(grabServoClosedPos);
            liftServo.setPosition(liftServoClosedPos);


            while (timer.milliseconds() - sequenceTimer < 800) {

            }
            rotationrotationServo.setPosition(rotrotStartingpos);
            currentSequence = 4;

        }

    }
    //класс для перемещение захвата в позицию подвеса образцов на перекладину
    public void RollOutSeq(){
        if (currentSequence==4) {

            sequenceTimer = timer.milliseconds();
            rotationrotationServo.setPosition(rotrotFishingpos);

            if (timer.milliseconds() - sequenceTimer >= 500) {
                rotationServo.setPosition(rotmidpos);
                currentSequence = 0;
            }

        }

    }
    //класс для позиции захвата
    public void AimingSeq(){
        if (currentSequence == 1){

            rotationrotationServo.setPosition(rotrotAimingpos);

            LiftGoToOP(330);

            liftServo.setPosition(liftServoOpenedPos);
            if (timer.milliseconds() - sequenceTimer >= 500) {
                currentSequence = 0;
            }


        }
    }
    //класс для позиций захвата
    public void FishingSeq(){
        if (currentSequence == 2){

            LiftGoToOP(50);

            rotationrotationServo.setPosition(rotrotFishingpos);

            liftServo.setPosition(liftServoOpenedPos);
            if (timer.milliseconds() - sequenceTimer >= 500) {
                currentSequence = 0;
                currentOperationValue = normalOperationValue;
            }


        }
    }
    //класс для позиции захвата
    public void ScoringSeq(){
        if (currentSequence == 3){

            LiftGoToOP(850);
            if (timer.milliseconds() - sequenceTimer >= 500) {
                rotationrotationServo.setPosition(rotrotScoringingpos);

                liftServo.setPosition(liftServoClosedPos);

                currentOperationValue = normalOperationValue;

                currentSequence = 0;
            }


        }
    }


    public void Fishing(Gamepad g2) {
        if(g2.left_bumper & timer.milliseconds() - rotTimer >= 0.0001){
            if (currentRotPos<rotmaxpos){currentRotPos += 0.02;}
            rotTimer = timer.milliseconds();

        } else if(g2.right_bumper & timer.milliseconds() - rotTimer >= 0.0001){
            if (currentRotPos>rotminpos){currentRotPos -= 0.02;}
            rotTimer = timer.milliseconds();

        } else if (g2.right_stick_button) {
            currentRotPos=rotmidpos;


        }
        rotationServo.setPosition(currentRotPos);
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

