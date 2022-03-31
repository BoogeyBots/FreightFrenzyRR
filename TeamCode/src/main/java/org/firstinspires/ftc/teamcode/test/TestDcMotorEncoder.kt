package org.firstinspires.ftc.teamcode.test

import com.acmerobotics.dashboard.FtcDashboard
import com.acmerobotics.dashboard.config.Config
import com.qualcomm.robotcore.eventloop.opmode.Disabled
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.*
import com.qualcomm.robotcore.util.Range
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBOpMode
import org.firstinspires.ftc.teamcode.modules.TestModule


@Config
object RobotConstants {
    @JvmField var MAGIC_NUMBER = 32
    @JvmField var P_PID = 13.0
    @JvmField var D_PID = 1.0
    @JvmField var I_PID = 0.0
    @JvmField var F_PID = 12.0

    // other constants
}


@Disabled
@TeleOp()
class TestDcMotorEncoder : BBOpMode(){
    override val modules: Robot = Robot( setOf(TestModule(this)))
    lateinit var motor: DcMotorEx
    val isBusy get() = motor.isBusy
    var maxPos = 2000
    var minPos = -2000
    var target = 0

    override fun init() {
        val dashboard = FtcDashboard.getInstance()
        telemetry = dashboard.telemetry

        motor = hardwareMap.get(DcMotorEx::class.java, "motor_spin")

        motor.targetPosition = 0

        // ORIGINAL PIDF: p=9.999847 i=2.999954 d=0.000000 f=0.000000
        motor.mode = DcMotor.RunMode.STOP_AND_RESET_ENCODER
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.setVelocityPIDFCoefficients(RobotConstants.P_PID, RobotConstants.I_PID, RobotConstants.D_PID, RobotConstants.F_PID)
        motor.power = 1.0

        //motor.targetPosition = (0.05 * COUNTS_PER_REV).toInt()
    }

    override fun loop() {
        if(gamepad1.a){
            goUp()
        }
        if (gamepad1.b){
            goDown()
        }

        motor.setVelocityPIDFCoefficients(RobotConstants.P_PID, RobotConstants.I_PID, RobotConstants.D_PID, RobotConstants.F_PID)
        telemetry.addData("TARGET POS", motor.targetPosition)
        telemetry.addData("CURRENT POS", motor.currentPosition)

        telemetry.update()
    }

    fun goUp() {
        if (motor.targetPosition in minPos  until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition + 130, minPos, maxPos)

    }
    fun goDown() {
        if (motor.targetPosition in minPos until (maxPos + 1))
            motor.targetPosition = Range.clip(motor.targetPosition - 130, minPos, maxPos)
    }

}