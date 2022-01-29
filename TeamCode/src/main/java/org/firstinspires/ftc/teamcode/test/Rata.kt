package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime

@TeleOp(name = "TeleOp rata")
class BasicTeleOp : LinearOpMode() {

    private var rata: CRServo? = null
    var poz: Double = 0.0
    var power1: Double = 0.0
    var power2 : Double =0.0
    var timex = ElapsedTime()
    var motor : DcMotor? = null

    override fun runOpMode() {
        rata = hardwareMap.get(CRServo::class.java, "rata")
        motor = hardwareMap.get(DcMotor::class.java, "motor")

        motor?.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        waitForStart()

        while (opModeIsActive()) {

            if(gamepad1.x ) {
                rata?.power = -1.0
            }
            else
                rata?.power = 0.0
            if(gamepad1.y) {
                motor?.power = -0.45
            }
            else
                motor?.power = 0.0





            telemetry.addData("Servo", rata?.power)
            telemetry.update()

            telemetry.addData("DcMotor", motor?.power)
            telemetry.update()

        }

    }
}