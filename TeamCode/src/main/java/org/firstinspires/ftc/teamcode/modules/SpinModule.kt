package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.HardwareDevice

class SpinModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val motor get() = get<DcMotorEx>("motor_spin")

    override fun init() {
        components["motor_spin"] = hardwareMap!!.get(DcMotorEx::class.java, "motor_spin")

        motor.targetPosition = 0
        // ORIGINAL PIDF: p=9.999847 i=2.999954 d=0.000000 f=0.000000
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.setVelocityPIDFCoefficients(13.0, 0.0, 1.0, 12.0)
        motor.power = 1.0
    }

    fun move_left(){
        motor.targetPosition = 2300
    }

    fun move_right(){
        motor.targetPosition = -2300
    }

    fun move_init(){
        motor.targetPosition = 0
    }
}