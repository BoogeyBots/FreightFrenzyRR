package org.firstinspires.ftc.teamcode.robot.auto.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.acmerobotics.roadrunner.geometry.Vector2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.*
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence




@Autonomous
class AutoTest : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(
        DuckModule(this),
        IntakeModule(this), MotorLiftModule(this), ServoLiftModule(this), ServoRidicareLift(this), SpinModule(this)
    ))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        modules.modules.forEach(){
            it.init()
        }

        val startPose = Pose2d(10.0, -65.5, 0.0)
        drive.poseEstimate = startPose

        val trajSeq: TrajectorySequence = drive.trajectorySequenceBuilder(startPose)
            .addTemporalMarker{
                liftState = LIFT_AUTO.START
            }
            .UNSTABLE_addTemporalMarkerOffset(0.4){
                get<SpinModule>().move_right()
            }
            .lineTo(Vector2d(-10.0, -60.5))
            .addTemporalMarker {
                liftState = LIFT_AUTO.EXTEND
            }
            .build()

        drive.followTrajectorySequenceAsync(trajSeq)

        get<ServoLiftModule>().move_close()

        waitForStart()

        if (isStopRequested) return

        while (opModeIsActive()){
            drive.update()
            update_lift()
        }

    }


    fun update_lift() {
        when (liftState) {
            LIFT_AUTO.IDLE -> {
                if (get<IntakeModule>().intakeState == IntakeModule.FSM.INTAKE_MIDDLE) {
                    telemetry.addData("TE ROG APARI", "pls")
                    telemetry.update()
                    liftState = LIFT_AUTO.START
                    timer.reset()
                }
            }
            LIFT_AUTO.START -> {
                if (timer.milliseconds() > 800.0) {
                    get<ServoLiftModule>().move_close()
                    get<ServoLiftModule>().move_extend()
                    get<ServoRidicareLift>().move_down()
                    timer.reset()
                }

            }

            LIFT_AUTO.EXTEND -> {
                    get<MotorLiftModule>().extend()
                    liftState = LIFT_AUTO.UP

            }

            LIFT_AUTO.UP -> {
                if (timer.milliseconds() > 1500.0) {
                        get<ServoLiftModule>().move_open()
                        liftState = LIFT_AUTO.UP_SERVO_LIFT
                        timer.reset()

                }
            }

            LIFT_AUTO.UP_SERVO_LIFT -> {
                if (timer.milliseconds() > 750.0) {
                    get<ServoLiftModule>().move_inside()
                    liftState = LIFT_AUTO.UP_MID
                    timer.reset()
                }
            }

            LIFT_AUTO.UP_MID -> {
                if (timer.milliseconds() > 300.0) {
                    get<MotorLiftModule>().go_intake()
                    get<SpinModule>().move_init()
                    liftState = LIFT_AUTO.BACK
                    timer.reset()
                }
            }
            LIFT_AUTO.BACK -> {
                if (timer.milliseconds() > 300.0) {
                    get<ServoRidicareLift>().move_intake()
                    liftState = LIFT_AUTO.IDLE
                    timer.reset()
                    get<IntakeModule>().intakeState = IntakeModule.FSM.INTAKE_JOS
                }
            }
        }
    }
    
    companion object {
        var liftState = LIFT_AUTO.IDLE

        val timer = ElapsedTime()

        enum class LIFT_AUTO {
            IDLE,
            START,
            EXTEND,
            UP_SERVO_LIFT,
            UP,
            UP_MID,
            BACK
        }


    }
}