package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

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

                LT.RollOutSeq();
                LT.AimingSeq();
                LT.FishingSeq();
                LT.ScoringSeq();

                LT.Fishing(gamepad1);
                LT.SequenceSwitch(gamepad2);
                LT.StickMover(gamepad2);
                LT.GrabAction(gamepad2);
                LT.LiftAction(gamepad2);
                MDT.OpMode(gamepad1);



            telemetry.addData("currentOPVAL: ", LT.currentOperationValue);
            telemetry.addData("BackRPOS", MDT.backRightMotor.getCurrentPosition());
            telemetry.addData("BackLPOS", MDT.backLeftMotor.getCurrentPosition());
            telemetry.addData("frontRPOS", MDT.frontRightMotor.getCurrentPosition());
            telemetry.addData("frontLPOS", MDT.frontLeftMotor.getCurrentPosition());
            telemetry.addData("ImuAngleYaw", MDT.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES));
            telemetry.addData("ImuAngleRoll", MDT.imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES));
            telemetry.addData("ImuAnglePitch", MDT.imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES));

            telemetry.update();
            }



        }
    }
