package org.firstinspires.ftc.teamcode.applications;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Sample", group = "Inactive")
public class Sample extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

        }
    }
}