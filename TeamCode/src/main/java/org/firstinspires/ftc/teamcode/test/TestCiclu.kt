package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.BBOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.*


@TeleOp
class TestCiclu : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(IntakeModule(this), MotorLiftModule(this), ServoLiftModule(this), ServoRidicareLift(this), SpinModule(this)))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)

        get<IntakeModule>().init()
        get<MotorLiftModule>().init()
        get<ServoLiftModule>().init()
        get<ServoRidicareLift>().init()
        get<SpinModule>().init()
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)


        while(!isStopRequested) {
            drive.setWeightedDrivePower(
                Pose2d(
                    (-gamepad1.left_stick_y).toDouble(),
                    (-gamepad1.left_stick_x).toDouble(),
                    (-gamepad1.right_stick_x).toDouble()
                )
            )

            drive.update()

            if (get<IntakeModule>().detect()) {
                get<MotorLiftModule>().go_intake()
                get<ServoLiftModule>().move_open()
                get<ServoLiftModule>().move_inside()
                get<ServoRidicareLift>().move_intake()
                get<IntakeModule>().has_detected = true
                servo_lift_down = false
            }

            get<IntakeModule>().move_on_detect()


            when(liftState){
                LIFT_AUTO.IDLE ->{
                    if(get<IntakeModule>().intakeState == IntakeModule.FSM.INTAKE_JOS) {
                        telemetry.addData("TE ROG APARI", "pls")
                        telemetry.update()
                        liftState = LIFT_AUTO.START
                        timer.reset()
                    }
                }
                LIFT_AUTO.START ->{
                    if(timer.milliseconds() > 800.0){
                        get<ServoLiftModule>().move_close()
                        get<ServoLiftModule>().move_extend()
                        get<ServoRidicareLift>().move_down()
                        servo_lift_down = true
                        liftState = LIFT_AUTO.IDLE
                    }
                }

            }


            if (gamepad1.a) {
                get<IntakeModule>().move_in()
            } else if (gamepad1.b) {
                get<IntakeModule>().move_out()
            } else {
                get<IntakeModule>().stop()
            }

            if (gamepad1.y) {
                if (lift_opened) {
                    get<ServoLiftModule>().move_close()
                    lift_opened = false
                } else {
                    get<ServoLiftModule>().move_open()
                    lift_opened = true

                }
            }

            if (gamepad1.dpad_down) {
                get<ServoRidicareLift>().move_down()
                servo_lift_down = true
            }
            if (gamepad1.dpad_up) {
                get<ServoRidicareLift>().move_up()
                get<SpinModule>().move_init()
                servo_lift_down = false
            }

            if (gamepad1.dpad_left) {
                get<ServoLiftModule>().move_inside()
            }
            if (gamepad1.dpad_right) {
                get<ServoLiftModule>().move_extend()
            }


            if (gamepad1.right_bumper) {
                get<MotorLiftModule>().extend()
            }

            if (gamepad1.left_bumper) {
                get<MotorLiftModule>().go_intake()
            }

            if(servo_lift_down) {

                if (gamepad1.left_trigger > 0.1) {
                    get<SpinModule>().move_left()
                }

                if (gamepad1.right_trigger > 0.1) {
                    get<SpinModule>().move_right()
                }
            }
            if(gamepad1.left_stick_button){
                get<SpinModule>().move_init()
            }

        }

    }


    companion object {
        val timer = ElapsedTime()

        var lift_opened = true
        var servo_lift_down = false

        enum class LIFT_AUTO {
            IDLE,
            START
        }

        var liftState = LIFT_AUTO.IDLE

    }

}