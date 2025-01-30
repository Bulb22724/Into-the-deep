package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TeleMain", group = "Active")
public class TeleMain extends LinearOpMode {
    private ElapsedTime timer = new ElapsedTime();
    private boolean flag = false;
    private double CT = 0;
    @Override
    public void runOpMode() throws InterruptedException {
         MechDriveTrain MDT = new MechDriveTrain();
         Lift LT = new Lift();

         LT.init(hardwareMap);
         MDT.init(hardwareMap);


        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            if(gamepad2.dpad_left) {
            flag = true;
            }
            if (!flag) {
                LT.StickMover(gamepad2);
                LT.GrabAction(gamepad2);
                LT.LiftAction(gamepad2);
                MDT.OpMode(gamepad1);
            }

            if(flag){
                CT = timer.milliseconds();

                sleep(5000);


                LT.leftLiftMotor.setVelocity(200);
                LT.rightLiftMotor.setVelocity(200);
                sleep(5000);
                }
            telemetry.addData("currentOPVAL: ", LT.currentOperationValue);
            telemetry.addData("BackRPOS", MDT.backRightMotor.getCurrentPosition());
            telemetry.addData("BackLPOS", MDT.backLeftMotor.getCurrentPosition());
            telemetry.addData("frontRPOS", MDT.frontRightMotor.getCurrentPosition());
            telemetry.addData("frontLPOS", MDT.frontLeftMotor.getCurrentPosition());
            telemetry.update();
            }



        }
    }
