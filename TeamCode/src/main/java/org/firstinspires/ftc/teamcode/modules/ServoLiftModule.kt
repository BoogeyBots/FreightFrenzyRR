package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class ServoLiftModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo_close = get<Servo>("servo_lift_close")
    val servo_arm = get<Servo>("servo_lift_arm")

    override fun init() {
        components["servo_lift_close"] = hardwareMap!!.get(Servo::class.java, "servo_lift_close")
        components["servo_lift_arm"] = hardwareMap!!.get(Servo::class.java, "servo_lift_arm")
    }

    fun move_close(){

    }

    fun move_open(){

    }

    fun move_extend(){

    }

    fun move_inside(){

    }


}