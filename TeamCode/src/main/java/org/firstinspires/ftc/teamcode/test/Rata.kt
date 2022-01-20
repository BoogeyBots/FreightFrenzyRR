package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo

@TeleOp(name = "TeleOp rata")
class BasicTeleOp : LinearOpMode() {

    private var rata: CRServo? = null
    var poz: Double = 0.0
    var power1: Double = 0.0
    override fun runOpMode() {
        rata = hardwareMap.get(CRServo::class.java, "rata")

        waitForStart()

        while (opModeIsActive()) {
            power1 = gamepad1.left_stick_x.toDouble()

            rata?.power = power1

            telemetry.addData("Power", power1)
            telemetry.update()
        }

    }
}