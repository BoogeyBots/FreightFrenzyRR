package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.util.Range

class MotorLiftModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val motor get() = get<DcMotorEx>("motor_lift")

    override fun init() {
        components["motor_lift"] = hardwareMap!!.get(DcMotorEx::class.java, "motor_lift")


        motor.targetPosition = 0
        // ORIGINAL PIDF: p=9.999847 i=2.999954 d=0.000000 f=0.000000
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.setVelocityPIDFCoefficients(15.0, 3.0, 0.0, 0.0)
        motor.power = 0.8
    }

    fun extend(){
        motor.targetPosition = 2700
    }

    fun goUp(){
        if (motor.targetPosition in minPos  until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition + 80, minPos, maxPos)

    }

    fun goDown(){
        if (motor.targetPosition in minPos until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition - 80, minPos, maxPos)
    }

    fun go_intake(){
        motor.targetPosition = 0
    }

    companion object{
        var maxPos = +2800
        var minPos = 0
    }
}