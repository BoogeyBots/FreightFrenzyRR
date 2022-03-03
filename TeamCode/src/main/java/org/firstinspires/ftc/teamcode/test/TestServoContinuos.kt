package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.Servo


@TeleOp()
class TestServoContinuos : LinearOpMode(){
    lateinit var servo1: CRServo

    override fun runOpMode() {
        servo1 = hardwareMap.get(CRServo::class.java, "servo1")

        waitForStart()

        while(opModeIsActive()){
            if(gamepad1.a){
                servo1.power = 1.0
            }
            if (gamepad1.b){
                servo1.power = -1.0
            }
        }
    }

}