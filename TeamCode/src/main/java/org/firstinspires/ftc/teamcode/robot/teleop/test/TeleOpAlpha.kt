package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.util.ElapsedTime
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.modules.*


@TeleOp
class TeleOpAlpha : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(DuckModule(this),IntakeModule(this), MotorLiftModule(this), ServoLiftModule(this), ServoRidicareLift(this), SpinModule(this), Ruleta(this)))


    override fun runOpMode() {
        val drive = SampleMecanumDrive(hardwareMap)

        get<DuckModule>().init()
        get<IntakeModule>().init()
        get<MotorLiftModule>().init()
        get<ServoLiftModule>().init()
        get<ServoRidicareLift>().init()
        get<SpinModule>().init()
        get<Ruleta>().init()
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
                    ((-gamepad1.left_stick_x).toDouble() / ((denominator_slow) * 3.0)),
                    (-gamepad1.right_stick_x).toDouble() / denominator_slow
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
                    if(get<IntakeModule>().intakeState == IntakeModule.FSM.INTAKE_MIDDLE) {
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
                        liftState = LIFT_AUTO.EXTEND
                        timer.reset()
                    }

                }

                LIFT_AUTO.EXTEND ->{
                    if(gamepad2.right_bumper)
                    {
                        get<MotorLiftModule>().extend()
                        liftState = LIFT_AUTO.UP
                    }
                }

                LIFT_AUTO.UP -> {
                    if(timer.milliseconds() > 800.0 ){
                        if (gamepad2.y) {
                            get<ServoLiftModule>().move_open()
                            liftState = LIFT_AUTO.UP_SERVO_LIFT
                            timer.reset()
                        }
                    }
                }

                LIFT_AUTO.UP_SERVO_LIFT ->{
                    if(timer.milliseconds() > 450.0){
                        get<ServoLiftModule>().move_inside()
                        liftState = LIFT_AUTO.UP_MID
                        timer.reset()
                    }
                }

                LIFT_AUTO.UP_MID ->{
                    if (timer.milliseconds() > 300.0){
                        get<MotorLiftModule>().go_intake()
                        get<SpinModule>().move_init()
                        liftState = LIFT_AUTO.BACK
                        timer.reset()
                    }
                }
                LIFT_AUTO.BACK ->{
                    if (timer.milliseconds() > 300.0){
                        get<ServoRidicareLift>().move_intake()
                        servo_lift_down = false
                        liftState = LIFT_AUTO.IDLE
                        timer.reset()
                        get<IntakeModule>().intakeState = IntakeModule.FSM.INTAKE_JOS
                    }
                }



            }


            if((get<IntakeModule>().intakeState == IntakeModule.FSM.INTAKE_START) && !get<IntakeModule>().has_detected) {
                if (gamepad1.a) {
                    get<IntakeModule>().move_in()
                }
                else {
                    get<IntakeModule>().stop()
                }
            }
            if (gamepad1.b) {
                get<IntakeModule>().move_out()
            }
            else{
                get<IntakeModule>().stop()
            }
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

            if(!bool_ruleta) {
                if (gamepad2.dpad_down) {
                    get<ServoRidicareLift>().move_down()
                    servo_lift_down = true
                }
                if (gamepad2.dpad_up) {
                    get<ServoRidicareLift>().move_up()
                    get<SpinModule>().move_init()
                    servo_lift_down = false
                }

                if (gamepad2.dpad_left) {
                    get<ServoLiftModule>().move_inside()
                }
                if (gamepad2.dpad_right) {
                    get<ServoLiftModule>().move_extend()
                }
            }

            if(bool_ruleta){
                if (gamepad2.dpad_up){
                    get<Ruleta>().increment_y(false)
                }
                else if (gamepad2.dpad_down){
                    get<Ruleta>().increment_y(true)
                }

                if(gamepad2.dpad_right){
                    get<Ruleta>().increment_x(true)
                }
                else if(gamepad2.dpad_left){
                    get<Ruleta>().increment_x(false)
                }
            }

            if(gamepad2.back && timer.milliseconds() > 500.0){
                bool_ruleta = !bool_ruleta
                timer.reset()
            }

            if (gamepad2.b){
                get<Ruleta>().move_cr(true)
            }
            else if(gamepad2.x){
                get<Ruleta>().move_cr(false)
            }
            else {
                get<Ruleta>().stop_cr()
            }
            /*
            if (gamepad1.right_bumper) {
                get<MotorLiftModule>().extend()
            }

             */

            if (gamepad2.left_bumper) {
                get<SpinModule>().move_init()
            }

            if(servo_lift_down) {

                if (gamepad2.left_trigger > 0.1) {
                    get<SpinModule>().move_left()
                }

                if (gamepad2.right_trigger > 0.1) {
                    get<SpinModule>().move_right()
                }
            }
            if(gamepad1.a){
                get<SpinModule>().move_init()
            }

            if(gamepad1.x){
                get<DuckModule>().move_counterclockwise()
            }
            else{
                get<DuckModule>().stop()
            }

            if(gamepad1.back && (timer_delimitare.seconds() > 1.0)){
                get<SpinModule>().move_delimitare()
                get<SpinModule>().move_init()
                timer_delimitare.reset()
            }

            telemetry.addData("DELIMITARE", SpinModule.delimitare)
            telemetry.update()
        }

    }


    companion object {
        val timer = ElapsedTime()
        val timer_delimitare = ElapsedTime()

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

        var bool_ruleta = false
    }

}