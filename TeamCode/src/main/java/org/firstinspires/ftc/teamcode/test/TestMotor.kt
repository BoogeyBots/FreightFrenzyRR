package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx

import com.qualcomm.robotcore.hardware.VoltageSensor
import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion
import kotlin.math.min


@Config
@TeleOp(name = "TeleOp Motor")
class testMotor : LinearOpMode() {




    private var dcmotor: DcMotorEx? = null
    override fun runOpMode() {

        val dashboard = FtcDashboard.getInstance()
        telemetry = dashboard.telemetry

        dcmotor = hardwareMap.get(DcMotorEx::class.java, "motor_spin")
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
            else {
                dcmotor?.power = 0.0
            }

            telemetry.addData("Power", dcmotor?.power)
            telemetry.addData("Voltage", getBatteryVoltage())
            telemetry.update()

        //  telemetry.addData("Power", power1)
        //    telemetry.update()
        }



    }

    fun getBatteryVoltage(): Double {
        var result = Double.POSITIVE_INFINITY
        for (sensor in hardwareMap.voltageSensor) {
            val voltage = sensor.voltage
            if (voltage > 0) {
                result = min(result, voltage)
            }
        }
        return result
    }
}

