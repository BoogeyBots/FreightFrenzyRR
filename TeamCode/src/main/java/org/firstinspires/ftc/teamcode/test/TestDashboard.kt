package org.firstinspires.ftc.teamcode

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorEx

/*
* Sine wave sample to demonstrate telemetry and config variables in action. Adjust the amplitude,
* phase, and frequency of the oscillation and watch the changes propagate immediately to the graph.
*/
@Disabled
@Config
@Autonomous
class SineWaveOpMode : LinearOpMode() {
    @Throws(InterruptedException::class)
    override fun runOpMode() {
        val dashboard = FtcDashboard.getInstance()
        telemetry = dashboard.telemetry

        waitForStart()
        if (isStopRequested) return
        while (opModeIsActive()) {
            telemetry.addData(
                "left trigger", gamepad1.left_trigger
            )
            telemetry.update()
        }
    }

    companion object {
        var AMPLITUDE = 10.0
        var PHASE = 90.0
        var FREQUENCY = 0.5
    }
}