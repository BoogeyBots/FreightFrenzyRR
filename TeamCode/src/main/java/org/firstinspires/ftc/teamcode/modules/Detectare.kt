package org.firstinspires.ftc.teamcode.vision

import com.acmerobotics.dashboard.FtcDashboard
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.HardwareDevice
import org.firstinspires.ftc.teamcode.modules.RobotModule
import org.opencv.core.*
import org.opencv.imgproc.Imgproc
import org.openftc.easyopencv.*
import org.openftc.easyopencv.OpenCvCamera.AsyncCameraOpenListener

class Detectare( override val opMode: OpMode) : OpenCvPipeline(), RobotModule {
    override var components: HashMap<String, HardwareDevice> = hashMapOf()

    override fun init() {
        val cameraMonitorViewId = hardwareMap!!.appContext
            .resources.getIdentifier(
                "cameraMonitorViewId",
                "id", hardwareMap!!.appContext.packageName
            )
        phoneCam = OpenCvCameraFactory.getInstance()
            .createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId)
        phoneCam?.setPipeline(this)
        phoneCam?.openCameraDeviceAsync(object : AsyncCameraOpenListener {
            override fun onOpened() {
                phoneCam?.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT)
                phoneCam?.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW)
                FtcDashboard.getInstance().startCameraStream(phoneCam, 0.0)
            }

            override fun onError(errorCode: Int) {}
        })
    }

    var mat = Mat()

    enum class Location {
        LEFT, RIGHT, NOT_FOUND, MID
    }

    var location: Location? = null
        private set

    override fun processFrame(input: Mat): Mat {
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV)
        val lowHSV = Scalar(20.0, 90.0, 0.0)
        val highHSV = Scalar(37.0, 255.0, 255.0)
        Core.inRange(mat, lowHSV, highHSV, mat)
        val left = mat.submat(LEFT_ROI)
        val right = mat.submat(RIGHT_ROI)
        val leftValue = Core.sumElems(left).`val`[0] / LEFT_ROI.area() / 255
        val rightValue = Core.sumElems(right).`val`[0] / RIGHT_ROI.area() / 255
        left.release()
        right.release()
        telemetry?.addData("Left raw value", Core.sumElems(left).`val`[0].toInt())
        telemetry?.addData("Right raw value", Core.sumElems(right).`val`[0].toInt())
        telemetry?.addData("Left percentage", Math.round(leftValue * 100).toString() + "'%%'")
        telemetry?.addData("Right percentage", Math.round(rightValue * 100).toString() + "'%%'")
        val stoneLeft = leftValue > PERCENT_COLOR_THRESHOLD
        val stoneRight = rightValue > PERCENT_COLOR_THRESHOLD
        if (stoneLeft) {
            location = Location.LEFT
            telemetry?.addData("Skystone Location", "right")
        } else if(stoneRight){
            location = Location.RIGHT
            telemetry?.addData("Skystone Location", "left")
        }
        else
            location = Location.MID
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB)
        val colorStone = Scalar(255.0, 0.0, 0.0)
        val colorSkystone = Scalar(0.0, 255.0, 0.0)
        Imgproc.rectangle(
            mat,
            LEFT_ROI,
            if (location == Location.LEFT) colorSkystone else colorStone
        )
        Imgproc.rectangle(
            mat,
            RIGHT_ROI,
            if (location == Location.RIGHT) colorSkystone else colorStone
        )
        return mat
    }

    @JvmName("getLocation1")
    fun getLocation(): Location? {
        return location
    }


    fun detect(): Location {
        when (getLocation()) {
            Location.LEFT -> {return Location.LEFT}
            Location.RIGHT -> {return Location.RIGHT}
            Location.MID -> {return Location.MID}

        }
        phoneCam!!.stopStreaming()
        return Location.MID
    }

    companion object {
        const val distance = 25.0

        val LEFT_ROI = Rect(
            Point(distance , 120.0),
            Point(distance + 40.0, 150.0)
        )
        val RIGHT_ROI = Rect(
            Point(distance + 200, 120.0),
            Point(distance + 240, 150.0)
        )
        var PERCENT_COLOR_THRESHOLD = 0.3


        var phoneCam: OpenCvCamera? = null
    }
}