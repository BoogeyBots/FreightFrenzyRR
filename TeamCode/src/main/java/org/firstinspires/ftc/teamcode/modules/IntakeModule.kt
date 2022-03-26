package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DistanceSensor
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.Servo
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit

class IntakeModule(override val opMode: OpMode) : RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    val dcmotor get() = get<DcMotor>("motor_intake")
    val servo_arm get() = get<Servo>("servo_senzor")
    val servo1 get() = get<Servo>("servo_intake1")
    val servo2 get() = get<Servo>("servo_intake2")
    val sensor_range get() = get<DistanceSensor>("sensor_range")

    val timer = ElapsedTime()

    var has_detected = false

    enum class FSM{
        INTAKE_START,
        OPPOSITE_MOTOR,
        STOP_MOTOR,
        ARM_SUS,
        INTAKE_MIDDLE,
        ARM_JOS,
        INTAKE_JOS
    }

    var intakeState = FSM.INTAKE_START



    override fun init() {
        components["motor_intake"] = hardwareMap!!.get(DcMotor::class.java, "motor_intake")
        components["servo_senzor"] = hardwareMap!!.get(Servo::class.java, "servo_senzor")

        components["servo_intake1"] = hardwareMap!!.get(Servo::class.java, "servo_intake1")
        components["servo_intake2"] = hardwareMap!!.get(Servo::class.java, "servo_intake2")
        components["sensor_range"] = hardwareMap!!.get(DistanceSensor::class.java, "sensor_range")

        dcmotor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        servo_arm.position = 0.8


        servo1.position = 0.82
        servo2.position = 0.18

        intakeState = FSM.INTAKE_START
    }

    fun intake_middle(){
        servo1.position = 0.5
        servo2.position = 0.5
    }

    fun intake_down(){
        servo1.position = 0.82
        servo2.position = 0.18
    }

    fun intake_up(){
        servo1.position = 0.2
        servo2.position = 0.8
    }

    fun move_in_auto() {
        dcmotor.power = -1.0
    }

    fun move_in(){
        dcmotor.power = -1.0
    }
    fun move_out() {
        dcmotor.power = 1.0
    }
    fun stop(){
        dcmotor.power = 0.0
    }

    fun detect(): Boolean {
        if(sensor_range.getDistance(DistanceUnit.CM) < 7.0){
            return true
        }
        return false
    }

    fun servo_up() {
        servo_arm.position = 0.0
    }
    fun servo_down(){
        servo_arm.position = 0.8
    }

    fun move_on_detect(){
        when(intakeState){
            FSM.INTAKE_START ->{
                if(has_detected){
                    timer.reset()
                    servo1.position = 0.2
                    servo2.position = 0.8
                    servo_arm.position = 0.0
                    dcmotor.power = -1.0
                    intakeState = FSM.STOP_MOTOR

                }
            }
            FSM.OPPOSITE_MOTOR ->{
                if(timer.milliseconds() > 400.0) {
                    dcmotor.power = 0.0
                    intakeState = FSM.STOP_MOTOR
                }
            }
            FSM.STOP_MOTOR -> {
                if(timer.milliseconds() > 400.0){
                    timer.reset()
                    dcmotor.power = 0.0
                    intakeState = FSM.ARM_SUS
                }
            }
            FSM.ARM_SUS -> {
                if(timer.milliseconds() > 300.0){
                    timer.reset()
                    intakeState = FSM.INTAKE_MIDDLE
                }
            }
            FSM.INTAKE_MIDDLE ->{
                if (timer.milliseconds() > 500.0){
                    servo1.position = 0.5
                    servo2.position = 0.5
                    telemetry?.addData("AM AJUNS", 2)
                    telemetry?.update()
                    timer.reset()
                    intakeState = FSM.ARM_JOS
                }
            }
            FSM.ARM_JOS -> {
                if(timer.milliseconds() > 100.0){
                    timer.reset()
                    has_detected = false
                }
            }
            FSM.INTAKE_JOS -> {
                servo1.position = 0.82
                servo2.position = 0.18
                servo_arm.position = .8

                intakeState = FSM.INTAKE_START
            }

        }
    }

    companion object{
        var is_up = false
        var element_gone = false



    }
    // TODO De modificat puterile motorului
}