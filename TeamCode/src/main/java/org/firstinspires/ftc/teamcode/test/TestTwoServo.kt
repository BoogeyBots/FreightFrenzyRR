package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.modules.TestModule

@TeleOp(name = "Test Two Servo", group = "TEST")
class TestTwoServo : BBLinearOpMode() {
    override val modules = Robot(setOf(TestModule(this)))
    var resolution = 0.00005
    var resChangeSpeed = 0.00000001

    lateinit var servo1: Servo
    lateinit var servo2: Servo


    override fun runOpMode() {
        servo1 = hardwareMap.get(Servo::class.java, "servo_ridicare_lift1")
        servo2 = hardwareMap.get(Servo::class.java, "servo_ridicare_lift2")

        servo1.position = 0.5
        servo2.position = 0.5

        waitForStart()

        while (opModeIsActive()) {
            resolution += when {
                gamepad1.dpad_up -> resChangeSpeed
                gamepad1.dpad_down -> -resChangeSpeed
                else -> 0.0
            }

            servo1.position = when {
                gamepad1.y -> servo1.position + resolution
                gamepad1.a -> servo1.position - resolution
                else -> servo1.position
            }

            servo2.position = 1.0 - servo1.position


            telemetry.addData("res", resolution)
            telemetry.addData("servo 1", servo1.position)
            telemetry.addData("servo 2", servo2.position)

            telemetry.update()
        }
    }
}