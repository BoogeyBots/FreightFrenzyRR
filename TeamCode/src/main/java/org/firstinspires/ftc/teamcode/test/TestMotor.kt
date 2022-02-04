package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple

@TeleOp(name = "TeleOp Motor")
class testMotor : LinearOpMode() {

    private var dcmotor: DcMotor? = null
    override fun runOpMode() {
        dcmotor = hardwareMap.get(DcMotor::class.java, "intake")
        dcmotor?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        dcmotor?.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        dcmotor?.mode = DcMotor.RunMode.RUN_WITHOUT_ENCODER
        waitForStart()

        while (opModeIsActive()) {
            if(gamepad1.left_trigger > 0.02){
                dcmotor?.power = -gamepad1.left_trigger.toDouble()
            }
            else if(gamepad1.right_trigger > 0.02){
                dcmotor?.power = gamepad1.right_trigger.toDouble()
            }
            else
                dcmotor?.power = 0.0
            telemetry.addData("Power", dcmotor?.power)
            telemetry.addData("Pos", dcmotor?.currentPosition)
            telemetry.update()

        //  telemetry.addData("Power", power1)
        //    telemetry.update()
        }

    }
}