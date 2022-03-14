package org.firstinspires.ftc.teamcode.robot.auto.test


import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.bbopmode.BBLinearOpMode
import org.firstinspires.ftc.teamcode.bbopmode.get
import org.firstinspires.ftc.teamcode.modules.*

@Autonomous
class AutoDetectare : BBLinearOpMode() {
    override val modules: Robot = Robot(
        setOf(
            Detectare(this)
        )
    )

    lateinit var position: Detectare.Location


    override fun runOpMode() {
        modules.modules.forEach(){
            it.init()
        }


        while (!isStarted) {
            position = get<Detectare>().detect()

            telemetry.addData("POZITIE", position)
            telemetry.update()
        }

        position = get<Detectare>().detect()

        waitForStart()

        while (opModeIsActive()){
            telemetry.addData("Pozitie", position)
            telemetry.update()
        }


    }
}
