package objects.lights

import objects.Material
import objects.renderableObjects.RenderableObj
import objects.renderableObjects.Sphere
import util.Color
import util.Ray
import util.Vector
import kotlin.math.asin
import kotlin.math.sign
import kotlin.random.Random

class SphereLight(private val position: Vector, private val radius: Double, color: Color, strength: Double) : Light {

    private val sphere = Sphere(position, radius, Material(Color(.0, .0, .0), color, strength))

    // TODO: Stratefied?
    override fun getRandomPoint(): Vector {
        val u = Random.nextDouble()
        val v = Random.nextDouble()
        val t = 2 * Math.PI * u
        val p = asin(2 * v * v - 1)
        return position + Vector(t, p) * radius
    }

    override fun invertNormals() {
        sphere.invertNormals()
    }

    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        return sphere.intersect(ray)
    }

}