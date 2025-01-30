package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
            LT.StickMover(gamepad2);
            LT.LiftAction(gamepad2);
            LT.GrabAction(gamepad2);
            telemetry.addData("left", LT.leftLiftMotor.getCurrentPosition());
            telemetry.addData("right", LT.rightLiftMotor.getCurrentPosition());
            telemetry.addData("grab", LT.grabServo.getPosition());
            telemetry.addData("lift", LT.liftServo.getPosition());
            telemetry.update();
        }
    }
}