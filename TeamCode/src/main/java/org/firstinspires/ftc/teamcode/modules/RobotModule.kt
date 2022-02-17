package org.firstinspires.ftc.teamcode.modules

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.robotcore.external.Telemetry
import java.lang.Exception


interface RobotModule {
    var components: HashMap<String, HardwareDevice>
    val opMode: OpMode
    val hardwareMap: HardwareMap? get() = opMode.hardwareMap
    val telemetry: Telemetry? get() = opMode.telemetry
    val linearOpMode get() = opMode as LinearOpMode


    // NU UITA SA APELEZI ASTA PESTE TOT!!!
    fun init() { }

    fun <T: HardwareDevice> get(name: String): T = components[name] as T
}

