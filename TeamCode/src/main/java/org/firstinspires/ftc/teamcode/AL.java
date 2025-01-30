package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;


//@Autonomous(group = "Red", name="")
@Autonomous(group = "Autonomous", name = "AL")
//@Disabled
@Config
public class AL extends LinearOpMode {
    //
    private ElapsedTime timer = new ElapsedTime();
    public static double FD1= 1800;
    public static double R1= 1200;
    public static int ELEV= 750;
    public static double FD2= 500;
    public static int DECL= 200;




    private double CT = 0;
    @Override
    public void runOpMode() {
        Lift LT = new Lift();
        MechDriveTrain MDT = new MechDriveTrain();
        // единожды исполняемые действия перед инициализацией
//        while (opModeInInit()){ // многократно исполняемые действия после инициализации
//
//        }
        // единожды исполняемые действия после инициализацией
        LT.init(hardwareMap);
        MDT.init(hardwareMap);
        waitForStart();
        // единожды исполняемые действия после старта
        if (opModeIsActive()) { // единожды исполняемые действия после старта

            LT.leftLiftMotor.setTargetPosition(ELEV);
            LT.rightLiftMotor.setTargetPosition(ELEV);

            LT.leftLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            LT.rightLiftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            LT.leftLiftMotor.setPower(0.2);
            LT.rightLiftMotor.setPower(0.2);

        LT.liftServo.setPosition(0.45);
        MDT.Forward(900, FD1);
        MDT.Right(900, R1);


        CT = timer.milliseconds();

        sleep(2500);
        MDT.Forward(900,FD2);

        LT.leftLiftMotor.setTargetPosition(DECL);
        LT.rightLiftMotor.setTargetPosition(DECL);

        sleep(2500);

        LT.grabServo.setPosition(0.85);

        sleep(2500);

        MDT.Back(900, FD2);

        LT.leftLiftMotor.setTargetPosition(-5);
        LT.rightLiftMotor.setTargetPosition(-5);

        sleep(5000);



        }

    }
}