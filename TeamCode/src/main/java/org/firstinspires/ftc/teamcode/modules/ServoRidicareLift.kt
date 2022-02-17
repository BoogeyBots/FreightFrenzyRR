package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class ServoRidicareLift(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo1 get()= get<Servo>("servo_ridicare_lift1")
    val servo2 get()= get<Servo>("servo_ridicare_lift2")

    override fun init() {
        components["servo_ridicare_lift1"] = hardwareMap!!.get(Servo::class.java, "servo_ridicare_lift1")
        components["servo_ridicare_lift2"] = hardwareMap!!.get(Servo::class.java, "servo_ridicare_lift2")

        servo1.position = listPos[0].first
        servo2.position = listPos[0].second
    }

    fun move_down(){
        if(currentPos < listPos.size - 1)
        currentPos += 1
        servo1.position = listPos[currentPos].first
        servo2.position = listPos[currentPos].second
    }

    fun move_up(){
        if(currentPos > 0)
            currentPos -= 1
        servo1.position = listPos[currentPos].first
        servo2.position = listPos[currentPos].second
    }

    fun move_intake(){
        servo1.position = listPos[0].first
        servo2.position = listPos[0].second
    }

    companion object{
        val listPos: List<Pair<Double,Double>> = listOf(Pair(0.165,0.83), Pair(0.45,0.55))
        var currentPos = 0
    }
}
