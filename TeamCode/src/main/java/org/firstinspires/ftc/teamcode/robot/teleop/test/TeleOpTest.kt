package org.firstinspires.ftc.teamcode.robot.teleop.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.IntakeModule

@TeleOp
class TeleOpTest : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(IntakeModule(this)))

    override fun runOpMode() {
        // val drive = SampleMecanumDrive(hardwareMap)
        modules.init()

        waitForStart()

        while (!isStopRequested)
        {
            /*
            if (gamepad1.right_trigger > 0.0) {
                forwardMovement = gamepad1.right_trigger.toDouble()
            } else if (gamepad1.left_trigger > 0.0) {
                forwardMovement = -gamepad1.left_trigger.toDouble()
            } else {
                forwardMovement = .0
            }
            drive.setWeightedDrivePower(
                Pose2d(
                    forwardMovement,
                    (-gamepad1.left_stick_x).toDouble(),
                    (-gamepad1.right_stick_x).toDouble()
                )
            )

            drive.update()


             */
            if(gamepad1.right_bumper){
                get<IntakeModule>().intake_up()
            }
            else if(gamepad1.left_bumper){
                get<IntakeModule>().intake_down()
            }
            if (gamepad1.a){
                get<IntakeModule>().intake_in()
            }
            else if(gamepad1.b){
                get<IntakeModule>().intake_out()
            }
            else{
                get<IntakeModule>().intake_stop()
            }





        }

    }

    companion object{
        var forwardMovement: Double = 0.0
    }
}