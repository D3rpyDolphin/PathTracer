package objects

import objects.superSamplers.SuperSampler
import util.Ray
import util.Rotation
import util.Vector
import java.lang.Math.toRadians
import kotlin.math.tan
import kotlin.random.Random

/**
 * Generates rays for renderer
 * @see <a href="https://en.wikipedia.org/wiki/Ray_tracing_(graphics)">wikipedia.org</a>
 * */
class Camera {
    enum class Orientation(vector: Vector) {
        UP(Vector(.0, 1.0, .0)), DOWN(Vector(.0, -1.0, .0));

        private val vector: Vector

        init {
            this.vector = vector
        }

        fun getVector(): Vector {
            return vector
        }
    }

    private var position: Vector
    var width: Int
    var height: Int
    var superSampler: SuperSampler?

    private lateinit var qx: Vector
    private lateinit var qy: Vector
    private lateinit var p11: Vector

    /**
     *
     * @param position position of camera
     * @param target center coordinate of the camera
     * @param orientation up or down
     * @param width
     * @param height
     * @param fov in degrees
     */
    constructor(position: Vector, target: Vector, orientation: Orientation, width: Int, height: Int, fov: Double, superSampler: SuperSampler? = null) {
        this.position = position
        this.width = width
        this.height = height
        this.superSampler = superSampler

        // TODO check
        val tnorm: Vector = (target-position).norm() // Relative forward vector
        val bnorm: Vector = (orientation.getVector() % tnorm).norm() // Relative right vector
        val vnorm: Vector = tnorm % bnorm // Relative up

        init(tnorm, bnorm, vnorm, fov)
    }

    /**
     * Angle based camera
     * @param position position of camera
     * @param rotation rotation of camera
     * @param width width of image in pixels
     * @param height height of image in pixels
     * @param fov in degrees
     */
    constructor(position: Vector, rotation: Rotation, width: Int, height: Int, fov: Double, superSampler: SuperSampler? = null) {
        this.position = position
        this.width = width
        this.height = height
        this.superSampler = superSampler

        val tnorm: Vector = -rotation.getRotatedAxis(Rotation.Axis.Y) // Relative forward vector
        val bnorm: Vector = rotation.getRotatedAxis(Rotation.Axis.X) // Relative right vector
        val vnorm: Vector = rotation.getRotatedAxis(Rotation.Axis.Z) // Relative up

        init(tnorm, bnorm, vnorm, fov)
    }

    private fun init(tnorm: Vector, bnorm: Vector, vnorm: Vector, fov: Double) {
        val gx = tan(toRadians(fov) / 2) // Actual half-width
        val gy = gx * (height - 1) / (width - 1) // Actual half-height

        // Get the top left ray
        p11 = tnorm - bnorm * gx + vnorm * gy

        qx  = bnorm * (2 * gx / (width - 1)) // Step vector right
        qy = vnorm * (2 * gy / (height - 1)) // Step vector up

        superSampler?.let {
            superSampler!!.init(qx, qy)
        }
    }

    /**
     *
     * @param i x-coordinate
     * @param j y-coordinate
     * @return
     */
    fun getPrimaryRay(i: Int, j: Int): Ray {
        // Ray but origin is at (0,0,0)
        val Rij = p11 + qx * i - qy * j

        superSampler?.let {
            Rij += superSampler!!.deviation()
        }

        // Normalize vector
        val rij: Vector = Rij.norm()
        return Ray(position, rij)
    }

    fun hasSuperSampler() = superSampler != null
}