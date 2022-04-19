package objects.superSamplers

import util.Vector

open class GridSuperSampler(private var numOfPoints: Int) : SuperSampler {

    private lateinit var superSamplingPoints: Array<Vector>
    lateinit var qpx: Vector
    lateinit var qpy: Vector

    override fun init(qx: Vector, qy: Vector) {
        qpx = qx / numOfPoints
        qpy = qy / numOfPoints

        var points = ArrayList<Vector>()

        val topLeft = -qx / 2.0 + qpx / 2.0   +   qy / 2.0 - qpy / 2.0
        for (j in 0 until numOfPoints) {
            for (i in 0 until numOfPoints) {
                points.add(topLeft + qpx * i - qpy * j)
            }
        }
        superSamplingPoints = points.toTypedArray()
    }

    var n = 0
    override fun deviation(): Vector {
        val dev = superSamplingPoints[n]
        n += 1
        n %= superSamplingPoints.size
        return dev
    }
}