package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorController
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBOpMode
import org.firstinspires.ftc.teamcode.modules.TestModule

@TeleOp()
class TestDcMotorEncoder : BBOpMode(){
    override val modules: Robot = Robot( setOf(TestModule(this)))
    lateinit var motor: DcMotorEx
    val isBusy get() = motor.isBusy
    var maxPos = 5500
    var minPos = -0
    var target = 0

    override fun init() {
        motor = hardwareMap.get(DcMotorEx::class.java, "motor")

        motor.targetPosition = 0

        // ORIGINAL PIDF: p=9.999847 i=2.999954 d=0.000000 f=0.000000
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.setVelocityPIDFCoefficients(15.0, 3.0, 0.0, 0.0)
        motor.power = 1.0
        //motor.targetPosition = (0.05 * COUNTS_PER_REV).toInt()
    }

    override fun loop() {
        if(gamepad1.a){
            goUp()
        }
        if (gamepad1.b){
            goDown()
        }

        telemetry.addData("TARGET POS", motor.targetPosition)
        telemetry.addData("CURRENT POS", motor.currentPosition)

        telemetry.update()


    }

    fun goUp() {
        if (motor.targetPosition in minPos  until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition + 100, minPos, maxPos)

    }
    fun goDown() {
        if (motor.targetPosition in minPos until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition - 100, minPos, maxPos)
    }

}