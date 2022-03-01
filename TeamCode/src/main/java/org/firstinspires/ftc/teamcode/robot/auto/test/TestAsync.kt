package org.firstinspires.ftc.teamcode.robot.auto.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive


@Autonomous
class TestAsync : OpMode(){


    override fun init() {
        val robot = SampleMecanumDrive(hardwareMap)
        val startPose = Pose2d(10.0, -65.5, 0.0)
        robot.poseEstimate = startPose

        val trajectory1 = robot.trajectoryBuilder(startPose)
            .lineTo(Vector2d(-10.0,-61.5))
            .build()


        robot.followTrajectoryAsync(trajectory1)

    }

    override fun loop() {
        telemetry.addData("IDKIDK TE ROG MERGI",  3)
        telemetry.update();
    }

}