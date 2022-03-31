package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.modules.Detectare
import org.firstinspires.ftc.teamcode.modules.Ruleta
import kotlin.math.absoluteValue

@TeleOp
class TestRuleta() : BBOpMode() {
    override val modules: Robot = Robot(setOf(Ruleta(this)))

    override fun init() {

        get<Ruleta>().init()
    }

    override fun loop() {
        /*
        if (gamepad1.dpad_up){
            get<Ruleta>().increment_y(true)
        }
        else if (gamepad1.dpad_down){
            get<Ruleta>().increment_y(false)
        }
        else{
            get<Ruleta>().stop_y()
        }

        if(gamepad1.dpad_right){
            get<Ruleta>().increment_x(true)
        }
        else if(gamepad1.dpad_left){
            get<Ruleta>().increment_x(false)
        }
        else{
            get<Ruleta>().stop_x()
        }


         */

        get<Ruleta>().increment_x(-gamepad1.left_stick_x.toDouble() / 4.0)
        get<Ruleta>().increment_y(gamepad1.left_stick_y.toDouble() * 0.007)
        when {
            gamepad1.a -> {
                get<Ruleta>().move_cr(true)
            }
            gamepad1.y -> {
                get<Ruleta>().move_cr(false)
            }
            else -> {
                get<Ruleta>().stop_cr()
            }
        }

        telemetry.addData("SERVO X/JOS", get<Ruleta>().servo_x.power)
        telemetry.addData("SERVO Y/SUS", get<Ruleta>().servo_y.position)
        telemetry.addData("SERVO CONTINUU", get<Ruleta>().cr_servo.power)
        telemetry.update()
    }
}