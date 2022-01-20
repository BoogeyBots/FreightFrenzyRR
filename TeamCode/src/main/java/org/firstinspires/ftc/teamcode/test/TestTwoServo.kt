package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo


@TeleOp()
class TestTwoServo : LinearOpMode(){
    private var servo1: Servo? = null
    private var servo2: Servo? = null

    override fun runOpMode() {
        servo1 = hardwareMap?.get(Servo::class.java, "servo1")
        servo2 = hardwareMap?.get(Servo::class.java, "servo2")

        waitForStart()

        while(opModeIsActive()){
            if(gamepad1.a){
                servo1!!.position = 0.84
                servo2!!.position = 0.16
            }
            if (gamepad1.b){
                servo1!!.position = 0.00
                servo2!!.position = 1.00
            }

            telemetry.addData("Servo 1 pozitie", servo1!!.position)
            telemetry.addData("Servo 2 pozitie", servo2!!.position)

            telemetry.update()
        }
    }

}