package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class Ruleta(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val servo_x get() = get<Servo>("servo_ruleta_x")
    val servo_y get() = get<Servo>("servo_ruleta_y")
    val cr_servo get() = get<CRServo>("servo_ruleta_cr")

    override fun init() {
        components["servo_ruleta_x"] = hardwareMap!!.get(Servo::class.java, "servo_ruleta_x")
        components["servo_ruleta_y"] = hardwareMap!!.get(Servo::class.java, "servo_ruleta_y")
        components["servo_ruleta_cr"] = hardwareMap!!.get(CRServo::class.java, "servo_ruleta_cr")

        servo_x.position = 0.65
        servo_y.position = 0.60
    }

    fun increment_x(forward: Boolean){
        if(forward) {
            servo_x.position += 0.004
        }
        else{
            servo_x.position -= 0.004
        }
    }

    fun increment_y(forward: Boolean){
        if (forward){
            servo_y.position += 0.004
        }
        else{
            servo_y.position -= 0.004
        }
    }

    fun move_cr(forward: Boolean){
        if (forward){
            cr_servo.power = 1.0
        }
        else{
            cr_servo.power = -1.0
        }
    }

    fun stop_cr(){
        cr_servo.power = -0.02

    }




}
