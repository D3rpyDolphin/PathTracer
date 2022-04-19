package objects.renderableObjects

import objects.Material
import util.Ray
import util.Vector
import kotlin.math.sqrt

// TODO: CHECK SPHERE THRESHOLD
open class Sphere(private val position: Vector, private val radius: Double, private val material: Material) :
    RenderableObj {
    private var ncoef = 1.0

    constructor(x: Double, y: Double, z: Double, radius: Double, material: Material) : this(
        Vector(x, y, z),
        radius,
        material
    )

    override fun invertNormals() {
        ncoef *= -1.0
    }

    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        val MIN_THRESH = 0.1

        val s = ray.origin
        val v = s - position
        val d = ray.direction.norm()
        val vdotd = v*d
        val v2 = v.magnitudeSqr
        val discriminant = vdotd * vdotd - (v2 - radius * radius)

        if (discriminant >= 0) {
            val t1 = -vdotd - sqrt(discriminant)
            val t2 = -vdotd + sqrt(discriminant)
            if (t2 > 0) {
                var t = Double.NaN
                if (t1 > 0) {
                    // t1 is closer than t2
                    t = t1
                } else if (t1 <= 0) {
                    // t1 is negative therefore get other point
                    // Ignore since this shows the instead of the sphere
                    t = t2
                }
                //System.out.println(t);
                if (t2 * d.magnitude >= MIN_THRESH) {
                    val pointHit = ray.getPoint(t)
                    val normalHit = normalAtPoint(pointHit) * ncoef
                    return RenderableObj.IntersectionResult(pointHit, normalHit, material)
                }
            }
        }
        return null
    }

    private fun normalAtPoint(p: Vector): Vector {
        return (p - position).norm()
    }
}