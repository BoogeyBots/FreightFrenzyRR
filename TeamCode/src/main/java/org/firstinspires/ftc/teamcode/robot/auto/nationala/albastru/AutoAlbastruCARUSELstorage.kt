package org.firstinspires.ftc.teamcode.robot.auto.nationala.albastru

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.*
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence
import org.firstinspires.ftc.teamcode.modules.Detectare


@Autonomous
class AutoAlbastruCARUSELstorage : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(
        DuckModule(this),
        IntakeModule(this), MotorLiftModule(this), ServoLiftModule(this), ServoRidicareLift(this), SpinModule(this), Detectare(this)
    ))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)
        modules.modules.forEach(){
            it.init()
        }


        val startPose = Pose2d(-34.0, 65.5, Math.toRadians(180.0))
        drive.poseEstimate = startPose


        get<ServoLiftModule>().move_open()
        get<IntakeModule>().servo_up()
        get<IntakeModule>().intake_up()
        liftState = LIFT_AUTO.IDLE



        val trajSeq: TrajectorySequence = drive.trajectorySequenceBuilder(startPose)
            .addTemporalMarker {
                get<DuckModule>().move_clockwise()
                get<IntakeModule>().intake_down()
            }
            .UNSTABLE_addTemporalMarkerOffset(0.5){
                get<ServoLiftModule>().move_close()
            }

            .waitSeconds(7.0)


            .lineToLinearHeading(Pose2d(-54.41, 62.4, Math.toRadians(+195.00)))
            .waitSeconds(2.00)
            .addTemporalMarker{
                get<DuckModule>().stop()
            }
            .setTangent(Math.toRadians(+195.00))

            .lineToLinearHeading(Pose2d(-59.0, 35.5, Math.toRadians(180.0)))

                  .build()

        drive.followTrajectorySequenceAsync(trajSeq)

        waitForStart()

        if (isStopRequested) return

        while (opModeIsActive()){
            drive.update()
            update_lift()
            get<IntakeModule>().move_on_detect()
            if (get<IntakeModule>().detect()) {
                get<MotorLiftModule>().go_intake()
                get<ServoLiftModule>().move_open()
                get<ServoLiftModule>().move_inside()
                get<ServoRidicareLift>().move_intake()
                get<IntakeModule>().has_detected = true

            }
        }

    }


    fun update_lift() {
        when (liftState) {
            LIFT_AUTO.IDLE -> {
                if (get<IntakeModule>().intakeState == IntakeModule.FSM.INTAKE_MIDDLE) {

                    liftState = LIFT_AUTO.START
                    timer.reset()
                }
            }

            LIFT_AUTO.START -> {
                if (timer.milliseconds() > 800.0) {
                    get<ServoLiftModule>().move_close()
                    get<ServoLiftModule>().move_extend()
                    get<ServoRidicareLift>().set_position(index_rata)

                    timer.reset()
                }

            }

            LIFT_AUTO.UP -> {
                if (timer.milliseconds() > 1500.0) {
                    get<ServoLiftModule>().move_open()
                    liftState = LIFT_AUTO.UP_SERVO_LIFT
                    timer.reset()
                }
            }

            LIFT_AUTO.UP_SERVO_LIFT -> {
                if (timer.milliseconds() > 500.0) {
                    get<SpinModule>().move_init()

                    liftState = LIFT_AUTO.UP_MID
                    timer.reset()
                }
            }

            LIFT_AUTO.UP_MID -> {
                if (timer.milliseconds() > 1000.0) {
                    get<ServoLiftModule>().move_inside()
                    get<MotorLiftModule>().go_intake()
                    liftState = LIFT_AUTO.BACK_SERVO
                    timer.reset()
                }
            }

            LIFT_AUTO.BACK_SERVO -> {
                if(timer.milliseconds() > 1000.0){
                    get<ServoRidicareLift>().move_intake()
                    liftState = LIFT_AUTO.BACK
                    timer.reset()
                }
            }

            LIFT_AUTO.BACK -> {
                if (timer.milliseconds() > 300.0) {
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

        lateinit var position: Detectare.Location

    }
}