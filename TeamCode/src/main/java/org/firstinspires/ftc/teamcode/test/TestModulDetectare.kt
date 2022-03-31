package org.firstinspires.ftc.teamcode.test

import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.modules.Detectare

@TeleOp(name = "TestModulDetectare")
class TestModulDetectare() : BBLinearOpMode() {
    override val modules: Robot = Robot(setOf(Detectare(this)))

    override fun runOpMode() {
        get<Detectare>().init()

        waitForStart()
        get<Detectare>().detect()
        while(opModeIsActive()){
            telemetry?.addData("Location: ", get<Detectare>().detect())
            telemetry?.update()
        }

    }

}