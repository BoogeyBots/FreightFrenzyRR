package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class ServoLiftModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo_close get()= get<Servo>("servo_lift_close")
    val servo_arm get()= get<Servo>("servo_lift_arm")

    override fun init() {
        components["servo_lift_close"] = hardwareMap!!.get(Servo::class.java, "servo_lift_close")
        components["servo_lift_arm"] = hardwareMap!!.get(Servo::class.java, "servo_lift_arm")

        servo_close.position = 0.4
        servo_arm.position = 0.22
    }


    fun move_close(){
        servo_close.position = 0.0
    }

    fun move_open(){
        servo_close.position = 0.4
    }

    fun move_extend(){
        servo_arm.position = 0.9
    }

    fun move_inside(){
        servo_arm.position = 0.22
    }


}