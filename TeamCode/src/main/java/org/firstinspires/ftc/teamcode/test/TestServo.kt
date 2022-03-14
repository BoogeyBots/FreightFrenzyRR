package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo


@TeleOp()
class TestServo : LinearOpMode(){
    lateinit var servo1: Servo

    override fun runOpMode() {
        servo1 = hardwareMap.get(Servo::class.java, "servo1")

        waitForStart()

        while(opModeIsActive()){
            if(gamepad1.a){
                servo1!!.position = 0.84
            }
            if (gamepad1.b){
                servo1!!.position = 0.00
            }

            telemetry.addData("Servo 1 pozitie", servo1!!.position)

            telemetry.update()
        }
    }

}