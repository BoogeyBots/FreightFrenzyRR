package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class DuckModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo get()= get<CRServo>("servo_rata")


    override fun init() {
        components["servo_rata"] = hardwareMap!!.get(CRServo::class.java, "servo_rata")
    }

    fun move_clockwise(){
        servo.power = 1.0
    }

    fun move_counterclockwise(){
        servo.power = -1.0
    }

    fun stop(){
        servo.power = 0.0
    }

    companion object{
    }
}
