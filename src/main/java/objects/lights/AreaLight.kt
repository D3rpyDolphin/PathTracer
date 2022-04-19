package objects.lights

import objects.Material
import objects.renderableObjects.Plane
import objects.renderableObjects.RenderableObj
import util.Color
import util.Ray
import util.Rotation
import util.Vector
import kotlin.random.Random

class AreaLight(center: Vector, xLength: Double, zLength: Double, rotation: Rotation = Rotation(.0, .0, .0),
                private val gridX: Int, private val gridZ: Int, color: Color, strength: Double) : Light {

    private val plane = Plane(center, xLength, zLength, rotation, Material(Color(.0, .0, .0), color, strength))

    private val bottomLeft = rotation.rotatePoint(center - Vector(xLength, 0.0, zLength) / 2.0, center)
    private val edgeX = rotation.rotatePoint(center + Vector(xLength, .0, .0)/2.0, center)/gridX.toDouble()
    private val edgeZ = rotation.rotatePoint(center + Vector(.0, .0, zLength)/2.0, center)/gridZ.toDouble()

    var i: Int = 0
    var j: Int = 0
    // Stratefied
    override fun getRandomPoint(): Vector {
        val point = bottomLeft + edgeX * (i + Random.nextDouble()) + edgeZ * (j + Random.nextDouble())

        // Update coordinates
        if (i % gridX == 0) {
            i = 0
            j++
        }
        j %= gridZ

        return point
    }

    override fun invertNormals() {
        plane.invertNormals()
    }

    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        return plane.intersect(ray)
    }

}