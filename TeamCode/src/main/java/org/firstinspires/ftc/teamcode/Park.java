package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;


//@Autonomous(group = "Red", name="")
@Autonomous(group = "Autonomous", name = "ParkR")
//@Disabled
public class Park extends LinearOpMode {
    //
    private ElapsedTime timer = new ElapsedTime();
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

        MDT.Right(900, 2500);
        LT.liftServo.setPosition(0.85);

        sleep(2000);





        }

    }
}