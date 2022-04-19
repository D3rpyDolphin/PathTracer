package objects.renderableObjects

import objects.Material
import util.Ray
import util.Rotation
import util.Vector

open class Plane(center: Vector, xLength: Double, zLength: Double,
                 rotation: Rotation = Rotation(0.0,0.0,0.0), material: Material) : RenderableObj {

    val mesh: Mesh

    init {
        val x = Vector(1.0, .0, .0) * xLength
        val z = Vector(.0, .0, 1.0) * zLength
        val bottomLeft = center - x/2.0 - z/2.0
        val triangle1 = Triangle(bottomLeft, bottomLeft + x, bottomLeft + z, material)
        val triangle2 = Triangle(bottomLeft + x, bottomLeft + x + z, bottomLeft + z, material)
        mesh = Mesh(listOf(triangle1, triangle2), center, rotation)
    }

    override fun invertNormals() {
        mesh.invertNormals()
    }

    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        return mesh.intersect(ray)
    }

}
