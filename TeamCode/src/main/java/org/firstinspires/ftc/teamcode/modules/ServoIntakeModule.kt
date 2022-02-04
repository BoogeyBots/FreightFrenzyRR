package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class ServoIntakeModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo get() = get<Servo>("servo_intake")
    val distanceSensor get() = get<DistanceSensor>("distance_sensor")

    override fun init() {
        components["servo_intake"] = hardwareMap!!.get(Servo::class.java, "servo_intake")
        components["distance_sensor"] = hardwareMap!!.get(Servo::class.java, "distance_sensor")

    }

    fun verify(){
        if( distanceSensor.getDistance(DistanceUnit.CM ) < 5.0){
            is_intake = true
        }
    }


    companion object{
        var is_intake = false
    }
}