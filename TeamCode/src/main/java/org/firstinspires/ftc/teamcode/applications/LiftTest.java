package org.firstinspires.ftc.teamcode.applications;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.modules.Lift;

@TeleOp(name = "LiftTest", group = "Test")
public class LiftTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        DcMotor left, right;
        Lift LT = new Lift();
        LT.init(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            LT.RollOutSeq();
            LT.AimingSeq();
            LT.FishingSeq();
            LT.ScoringSeq();

            LT.SequenceSwitch(gamepad2);
            LT.StickMover(gamepad2);
            LT.LiftAction(gamepad2);
            LT.GrabAction(gamepad2);
            LT.Fishing(gamepad2);

            telemetry.addData("left", LT.leftLiftMotor.getCurrentPosition());
            telemetry.addData("right", LT.rightLiftMotor.getCurrentPosition());
            telemetry.addData("grab", LT.grabServo.getPosition());
            telemetry.addData("rot", LT.rotationServo.getPosition());
            telemetry.addData("rotrot", LT.rotationrotationServo.getPosition());
            telemetry.addData("lift", LT.liftServo.getPosition());
            telemetry.addData("operatValue", LT.currentOperationValue);

            telemetry.update();
        }
    }
}