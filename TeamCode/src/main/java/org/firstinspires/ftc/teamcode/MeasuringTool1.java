package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Measuring Tool 1", group = "Active")
public class MeasuringTool1 extends LinearOpMode {
    private boolean flag = false;
    private double CT = 0.0;
    private int lastKey = 0;
    private double FDTime, LTTime, RTTime, BKTime = 0.0;
    private double velocity = 500;
    private ElapsedTime timer = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        MechDriveTrain MDT = new MechDriveTrain();
        MDT.init(hardwareMap);
        Lift LT = new Lift();
        LT.init(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if (gamepad1.y){
                if (!flag){
                    CT = timer.milliseconds();
                    flag = !flag;
                }
                MDT.frontLeftMotor.setVelocity(velocity);
                MDT.frontRightMotor.setVelocity(velocity);
                MDT.backLeftMotor.setVelocity(velocity);
                MDT.backRightMotor.setVelocity(velocity);

                lastKey = 1;
            }


            else if (gamepad1.a){
                if (!flag){
                    CT = timer.milliseconds();
                    flag = !flag;
                }
                MDT.frontLeftMotor.setVelocity(-velocity);
                MDT.frontRightMotor.setVelocity(-velocity);
                MDT.backLeftMotor.setVelocity(-velocity);
                MDT.backRightMotor.setVelocity(-velocity);

                lastKey = 2;
            }


            else if (gamepad1.b){
                if (!flag){
                    CT = timer.milliseconds();
                    flag = !flag;
                }
                MDT.frontLeftMotor.setVelocity(velocity);
                MDT.frontRightMotor.setVelocity(-velocity);
                MDT.backLeftMotor.setVelocity(-velocity);
                MDT.backRightMotor.setVelocity(velocity);

                lastKey = 3;
            }


            else if (gamepad1.x){
                if (!flag){
                    CT = timer.milliseconds();
                    flag = !flag;
                }
                MDT.frontLeftMotor.setVelocity(-velocity);
                MDT.frontRightMotor.setVelocity(velocity);
                MDT.backLeftMotor.setVelocity(velocity);
                MDT.backRightMotor.setVelocity(-velocity);

                lastKey = 4;
            }
            else{
                if (flag & lastKey==1) {
                    FDTime += timer.milliseconds() - CT;
                    flag = !flag;
                }
                else if (flag & lastKey==2) {
                    BKTime += timer.milliseconds() - CT;
                    flag = !flag;
                }
                else if (flag & lastKey==3) {
                    RTTime += timer.milliseconds() - CT;
                    flag = !flag;
                }
                else if (flag & lastKey==4) {
                    LTTime += timer.milliseconds() - CT;
                    flag = !flag;
                }

                MDT.frontLeftMotor.setVelocity(0);
                MDT.frontRightMotor.setVelocity(0);
                MDT.backLeftMotor.setVelocity(0);
                MDT.backRightMotor.setVelocity(0);
            }

            telemetry.addData("FDTime, ms: ", FDTime);
            telemetry.addData("BKTime, ms: ", BKTime);
            telemetry.addData("RTTime, ms: ", RTTime);
            telemetry.addData("LTTime, ms: ", LTTime);
            telemetry.update();
        }
    }
}