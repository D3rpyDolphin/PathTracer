package objects.renderableObjects

import objects.Material
import util.Ray
import util.Rotation
import util.Vector


class Triangle(var v0: Vector, var v1: Vector, var v2: Vector, private val material: Material) :
    RenderableObj {

    private var edge10: Vector
    private var edge20: Vector
    private var normal: Vector

    init {
        edge10 = v1 - v0
        edge20 = v2 - v0
        normal = (edge20 % edge10).norm()
    }

    private var nconst = 1.0

    override fun invertNormals() {
        nconst *= -1.0
    }

    private val EPSILON = 0.01
    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        val h = ray.direction % edge20
        val a = edge10 * h
        if (a > -EPSILON && a < EPSILON) {
            return null // This ray is parallel to this triangle.
        }

        val f = 1.0 / a
        val s = ray.origin - v0
        val u = s * h * f
        if (u < 0.0 || u > 1.0) {
            return null
        }

        val q = s % edge10
        val v = ray.direction * q * f
        if (v < 0.0 || u + v > 1.0) {
            return null
        }

        // At this stage we can compute t to find out where the intersection point is on the line.
        val t = edge20 * q * f
        return if (t > EPSILON) { // ray intersection
            RenderableObj.IntersectionResult(ray.getPoint(t), normal*nconst, material)
        } else {  // This means that there is a line intersection but not a ray intersection.
            null
        }
    }

    fun rotate(center: Vector, rotation: Rotation) {
        v0 = rotation.rotatePoint(v0, center)
        v1 = rotation.rotatePoint(v1, center)
        v2 = rotation.rotatePoint(v2, center)

        edge10 = v1 - v0
        edge20 = v2 - v0
        normal = (edge20 % edge10).norm()
    }
}