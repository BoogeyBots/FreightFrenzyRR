package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBOpMode
import org.firstinspires.ftc.teamcode.modules.TestModule

@TeleOp()
class TestLift : BBOpMode(){
    override val modules: Robot = Robot( setOf(TestModule(this)))
    lateinit var motor: DcMotorEx
    lateinit var servo: Servo
    lateinit var servo2: Servo

    val isBusy get() = motor.isBusy
    var maxPos = 0
    var minPos = -3000
    var target = 0

    override fun init() {
        motor = hardwareMap.get(DcMotorEx::class.java, "motor_lift")
        servo = hardwareMap.get(Servo::class.java, "servo_lift1")
        servo2 = hardwareMap.get(Servo::class.java, "servo_lift2")

        motor.targetPosition = 0

        // ORIGINAL PIDF: p=9.999847 i=2.999954 d=0.000000 f=0.000000
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.setVelocityPIDFCoefficients(15.0, 3.0, 0.0, 0.0)
        motor.power = 0.4

        servo.position = 0.5
        servo2.position = 0.5
        //motor.targetPosition = (0.05 * COUNTS_PER_REV).toInt()
    }

    override fun loop() {
        if(gamepad1.a){
            goUp()
        }
        if (gamepad1.b){
            goDown()
        }

        if(gamepad1.left_bumper){
            servo.position = Range.clip(servo.position + resolution, 0.0, 1.0)
            servo2.position = Range.clip(servo2.position - resolution, 0.0, 1.0)
        }
        if(gamepad1.right_bumper){
            servo.position = Range.clip(servo.position - resolution, 0.0, 1.0)
            servo2.position = Range.clip(servo2.position + resolution, 0.0, 1.0)
        }


        telemetry.addData("TARGET POS", motor.targetPosition)
        telemetry.addData("CURRENT POS", motor.currentPosition)
        telemetry.addData("SERVO 1 POS", servo.position)
        telemetry.addData("SERVO 2 POS", servo2.position)

        telemetry.update()


    }

    fun goUp() {
        if (motor.targetPosition in minPos  until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition + 10, minPos, maxPos)

    }
    fun goDown() {
        if (motor.targetPosition in minPos until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition - 10, minPos, maxPos)
    }

    companion object{
        var resolution = 0.0005
    }

}