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

    fun init() { }

}

inline fun <reified T: HardwareDevice> RobotModule.get(name: String): T {
    if(components[name] is T)
        return components[name] as T
    else{
        throw Exception()
    }
}