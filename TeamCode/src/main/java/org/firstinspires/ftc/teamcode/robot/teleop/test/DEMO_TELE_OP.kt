package org.firstinspires.ftc.teamcode.robot.teleop.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.*

@Disabled
@TeleOp
class DEMO_TELE_OP : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(DuckModule(this)))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)

        get<DuckModule>().init()
    drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER)


        liftState = LIFT_AUTO.IDLE
        timer_slow.reset()


        waitForStart()

        while(!isStopRequested) {
            if (gamepad1.right_trigger > 0.0) {
                forwardMovement = gamepad1.right_trigger.toDouble()
            } else if (gamepad1.left_trigger > 0.0) {
                forwardMovement = -gamepad1.left_trigger.toDouble()
            } else {
                forwardMovement = .0
            }
            drive.setWeightedDrivePower(
                Pose2d(
                    forwardMovement / denominator_slow,
                    (-gamepad1.left_stick_x).toDouble() / denominator_slow,
                    (-gamepad1.right_stick_x).toDouble() / denominator_slow
                )
            )

            drive.update()



            if(gamepad1.y and (timer_slow.seconds() > 0.7)){
                if(!robot_slow) {
                    denominator_slow = 2
                    robot_slow = true
                }
                if(robot_slow){
                    denominator_slow = 1
                    robot_slow = false
                }
                timer_slow.reset()
            }


            if(gamepad2.a){
                get<DuckModule>().move_counterclockwise()
            }
            else{
                get<DuckModule>().stop()
            }

        }

    }


    companion object {
        val timer = ElapsedTime()

        var lift_opened = true
        var servo_lift_down = false

        enum class LIFT_AUTO {
            IDLE,
            START,
            EXTEND,
            UP_SERVO_LIFT,
            UP,
            UP_MID,
            BACK
        }

        var liftState = LIFT_AUTO.IDLE
        var forwardMovement: Double = 0.0
        var denominator_slow = 1
        var robot_slow = false
        var timer_slow = ElapsedTime()
    }

}