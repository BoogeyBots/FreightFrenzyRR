package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo

class IntakeModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val motor get() = get<DcMotor>("intake")
    val servo1 get() = get<Servo>("intake_servo1")
    val servo2 get() = get<Servo>("intake_servo2")

    override fun init() {
        components["intake"] = hardwareMap!!.get(DcMotor::class.java, "intake")
        components["intake_servo1"] = hardwareMap!!.get(Servo::class.java, "intake_servo1")
        components["intake_servo2"] = hardwareMap!!.get(Servo::class.java, "intake_servo2")

        motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
    }

    fun intake_in() {
        if (!is_up) {
            motor.power = -1.0
        }
    }
    fun intake_out(){
        if(!is_up){
            motor.power = 1.0
        }
    }

    fun intake_stop() {
        motor.power = 0.0
    }

    fun intake_up() {
        servo1.position = 0.80
        servo2.position = 0.20
        is_up = true
    }

    fun intake_down(){
        servo1.position = 0.18
        servo2.position = 0.82
        is_up = false
    }

    companion object{
        var is_up = false
    }
    // TODO De modificat puterile motorului
}