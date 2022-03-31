package org.firstinspires.ftc.teamcode.robot.auto.nationala.albastru

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence

@Autonomous
class AutoAlbastruSimplu : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(
            ))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        modules.modules.forEach(){
            it.init()
        }


        val startPose = Pose2d(-39.0, 65.5, 0.0)
        drive.poseEstimate = startPose






        val trajSeq: TrajectorySequence = drive.trajectorySequenceBuilder(startPose)
            .lineTo(Vector2d(37.0,65.5))
            .lineTo(Vector2d(37.0,37.5))
            .build()

        drive.followTrajectorySequenceAsync(trajSeq)

        waitForStart()

        if (isStopRequested) return

        while (opModeIsActive()) {
            drive.update()
        }

    }



    companion object {

        val timer = ElapsedTime()

        enum class LIFT_AUTO {
            IDLE,
            START,

            UP_SERVO_LIFT,
            UP,
            UP_MID,
            BACK_SERVO,
            BACK
        }

        var index_rata = 1
        var index_rata_y = 0.0
        var index_rata_x = 0.0
        var index_heading = 0.0


    }
}