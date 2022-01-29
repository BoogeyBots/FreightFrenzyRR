package org.firstinspires.ftc.teamcode.bbopmode

import org.firstinspires.ftc.teamcode.Robot
import org.firstinspires.ftc.teamcode.modules.RobotModule

interface BBOpModeBase  {
    val modules: Robot
}

inline fun <reified T: RobotModule> BBOpModeBase.get(): T = modules.get()