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

    public static double FD1= 1850;
    public static double R1= 1200;
    public static int ELEV= 950;
    public static double FD2= 1500;
    public static int DECL= 300;
    public static double RIGHT = 600;
    public static double FD3 = 600;
    public static double RIGHT2 = 600;
    public static double BACK = 600;
    public static double FD4 = 600;
    public static double RIGHT3 = 600;
    public static double BACK2 = BACK;





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
        LT.LiftGoToAUTO(ELEV);

        LT.rotationrotationServo.setPosition(0.42);
        sleep(500);
        LT.rotationServo.setPosition(0.42);
        MDT.Forward(500, 0, 0, FD1);


        CT = timer.milliseconds();

        sleep(2500);

        LT.LiftGoToAUTO(DECL);

        sleep(2500);

        LT.grabServo.setPosition(0.62);

        sleep(2500);

        MDT.Forward(-500, 500, 0, 1850);
            MDT.Forward(0, 500, 0, 1000);

        LT.LiftGoToAUTO(-5);

        sleep(2000);

        //MDT.Right(500, RIGHT);
        //MDT.Forward(500, FD3);
        //MDT.Right(500, RIGHT2);
        //MDT.Back(500, BACK);
        //MDT.Forward(500,FD4);
        //MDT.Right(500,RIGHT3);
        //MDT.Back(500, BACK2);



        }

    }
}