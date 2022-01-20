package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor

@TeleOp(name = "TeleOp Motor")
class testMotor : LinearOpMode() {

    private var dcmotor: DcMotor? = null
    override fun runOpMode() {
        dcmotor = hardwareMap.get(DcMotor::class.java, "dcmotor")
        dcmotor?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

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


        //  telemetry.addData("Power", power1)
        //    telemetry.update()
        }

    }
}