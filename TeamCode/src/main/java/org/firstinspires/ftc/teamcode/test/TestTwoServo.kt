package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo


@TeleOp()
class TestTwoServo : LinearOpMode(){
    lateinit var servo1: Servo
    lateinit var servo2: Servo

    override fun runOpMode() {
        servo1 = hardwareMap.get(Servo::class.java, "intake_servo1")
        servo2 = hardwareMap.get(Servo::class.java, "intake_servo2")

        waitForStart()

        while(opModeIsActive()){
            if(gamepad1.a){
                servo1!!.position = 0.8
                servo2!!.position = 0.2
            }
            if (gamepad1.b){
                servo1!!.position = 0.2
                servo2!!.position = 0.8
            }

            telemetry.addData("Servo 1 pozitie", servo1!!.position)
            telemetry.addData("Servo 2 pozitie", servo2!!.position)

            telemetry.update()
        }
    }

}