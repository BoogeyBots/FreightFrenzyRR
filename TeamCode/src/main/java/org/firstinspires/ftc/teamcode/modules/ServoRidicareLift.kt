package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class ServoRidicareLift(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo1 = get<Servo>("servo_ridicare_lift1")
    val servo2 = get<Servo>("servo_ridicare_lift2")

    override fun init() {
        components["servo_ridicare_lift1"] = hardwareMap!!.get(Servo::class.java, "servo_ridicare_lift1")
        components["servo_ridicare_lift2"] = hardwareMap!!.get(Servo::class.java, "servo_ridicare_lift2")
    }

    fun move_up(){
        if(currentPos < listPos.size)
        currentPos += 1
        servo1.position = listPos[currentPos].first
        servo2.position = listPos[currentPos].second
    }

    companion object{
        val listPos: List<Pair<Double,Double>> = listOf(Pair(0.1,0.9), Pair(0.9,0.1))
        var currentPos = 0
    }
}
