package objects.renderableObjects

import objects.Material
import util.Ray
import util.Vector

interface RenderableObj {

    fun invertNormals()
    fun intersect(ray: Ray): IntersectionResult? // Must return [pointHit, normalHit]

    class IntersectionResult(
        val pointHit: Vector,
        val normalHit: Vector,
        val materialHit: Material
    )
}