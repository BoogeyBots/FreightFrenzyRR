package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareDevice

class SpinModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val motor = get<DcMotorEx>("motor_spin")

    override fun init() {
        components["motor_spin"] = hardwareMap!!.get(DcMotorEx::class.java, "motor_spin")
    }

    fun move_left(){
        motor.power = -1.0
    }

    fun move_right(){
        motor.power = 1.0
    }
}