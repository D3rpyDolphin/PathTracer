package objects.renderableObjects

import objects.Material
import util.Ray
import util.Rotation
import util.Vector

class RectangularPrism(center: Vector, xLength: Double, yLength: Double, zLength: Double,
                       rotation: Rotation = Rotation(.0, .0, .0), materials: Array<Material>): RenderableObj {

    private val mesh: Mesh

    init {
        val xVector = Vector(xLength, .0, .0)
        val yVector = Vector(.0, yLength, .0)
        val zVector = Vector(.0, .0, zLength)

        // relative to -z axis
        val bottomPlane = Plane(center - yVector/2.0, xLength, zLength, Rotation(180.0, .0, .0), materials[0])
        val topPlane = Plane(center + yVector/2.0, xLength, zLength, material = materials[1])
        val leftPlane = Plane(center - xVector/2.0, yLength, zLength, Rotation(.0, .0, -90.0), materials[2])
        val rightPlane = Plane(center + xVector/2.0, yLength, zLength, Rotation(.0, .0, 90.0), materials[3])
        val backPlane = Plane(center + zVector/2.0, xLength, yLength,  Rotation(-90.0, .0, .0), materials[4])
        val frontPlane = Plane(center - zVector/2.0, xLength, yLength, Rotation(90.0, .0, .0), materials[5])

        val triangles = ArrayList<Triangle>()
        triangles.addAll(bottomPlane.mesh.triangles)
        triangles.addAll(topPlane.mesh.triangles)
        triangles.addAll(leftPlane.mesh.triangles)
        triangles.addAll(rightPlane.mesh.triangles)
        triangles.addAll(backPlane.mesh.triangles)
        triangles.addAll(frontPlane.mesh.triangles)

        mesh = Mesh(triangles, center, rotation)
    }

    constructor(center: Vector, xLength: Double, yLength: Double, zLength: Double, rotation: Rotation, material: Material):
            this(center, xLength, yLength, zLength, rotation, Array<Material>(6, { _ -> material }))


    override fun invertNormals() {
        mesh.invertNormals()
    }

    override fun intersect(ray: Ray): RenderableObj.IntersectionResult? {
        return mesh.intersect(ray)
    }
}