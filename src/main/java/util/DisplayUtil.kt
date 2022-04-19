package util

import org.opencv.core.Core
import org.opencv.core.Mat
import org.opencv.core.CvType
import org.opencv.highgui.HighGui

object DisplayUtil {
    fun show(image: Image) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME)

        // convert
        val img = Mat(image.height, image.width, CvType.CV_8UC3)
        putPixels(img, image)

        // display
        HighGui.imshow("Final Render", img)
        HighGui.waitKey()
    }

    private fun putPixels(mat: Mat, image: Image) {
        for (j in 0 until image.height) {
            for (i in 0 until image.width) {
                val (r, g, b) = image[i, j] * 255.0
                val data = doubleArrayOf(b, g, r) // Takes in bgr so swap b and r
                mat.put(j, i, *data)
            }
        }
    }
}