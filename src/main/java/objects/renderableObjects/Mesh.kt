package objects.renderableObjects

import objects.Material
import util.Ray
import util.Rotation
import util.Vector

open class Mesh(val triangles: List<Triangle>, center: Vector = Vector(.0, .0, .0),
                rotation: Rotation = Rotation(.0, .0, .0)) : RenderableObj {

    init {
        rotate(center, rotation)

        // Split mesh into tree
    }

    override fun invertNormals() {
        for (triangle in triangles) {
            triangle.invertNormals()
        }
    }

    // TODO optimize
    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        var closest: RenderableObj.IntersectionResult? = null
        var minDistance = Double.MAX_VALUE
        for (triangle in triangles) {
            val intersection = triangle.intersect(ray)
            if (intersection != null) {
                val distance = ray.origin.dist(intersection.pointHit)
                if (distance < minDistance) {
                    minDistance = distance
                    closest = intersection
                }
            }
        }
        return closest
    }

    fun rotate(center: Vector, rotation: Rotation) {
        for (triangle in triangles) {
            triangle.rotate(center, rotation)
        }
    }
}