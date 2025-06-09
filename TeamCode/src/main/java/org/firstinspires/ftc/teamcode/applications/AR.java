package org.firstinspires.ftc.teamcode.applications;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.modules.MechDriveTrain;


//@Autonomous(group = "Red", name="")
@Autonomous(group = "Autonomous", name = "AR")
//@Disabled
@Config
public class AR extends LinearOpMode {
    //
    private ElapsedTime timer = new ElapsedTime();

    public static double LEFT = 2000;





    private double CT = 0;
    @Override
    public void runOpMode() {

        MechDriveTrain MDT = new MechDriveTrain();
        // единожды исполняемые действия перед инициализацией
//        while (opModeInInit()){ // многократно исполняемые действия после инициализации
//
//        }
        // единожды исполняемые действия после инициализацией

        MDT.init(hardwareMap);
        waitForStart();
        // единожды исполняемые действия после старта
        if (opModeIsActive()) { // единожды исполняемые действия после старта

        MDT.Left(500,LEFT);



        }

    }
}