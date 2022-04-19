package main.integrators

import main.Renderer
import main.Scene
import objects.renderableObjects.RenderableObj
import util.Color
import util.Ray
import util.Vector

class PathTracer(val scene: Scene, val maxDepth: Int) : Integrator {

    enum class Debug {
        NONE,
        DISTANCE,
        NORMALS
    }

    private val debug = Debug.NONE

    override fun calculate(ray: Ray, depth: Int): Color {
        if (depth >= maxDepth) {
            //counter++
            return Color(0.0, .0, .0)
        }

        // Recursive ray tracing algorithm //
        // Get intersection //
        // Nearest intersection
        val nearestIntersection = scene.nearest(ray)

        if (nearestIntersection == null) {
            // Or skybox
            //return Color(245.0, 81.0, 66.0, true)
            return Color(0.0, .0, .0)
        }

        val material = nearestIntersection.materialHit
        val normalHit = nearestIntersection.normalHit
        val pointHit = nearestIntersection.pointHit

        // Calculate contribution //

        when (debug) {
            Debug.DISTANCE -> {
                return Color(1.0, 1.0, 1.0) * ray.origin.dist(pointHit)
            }
            Debug.NORMALS -> {
                return Color(1.0, 1.0, 1.0) * (normalHit * Vector(.4, 0.75, .0))
            }
        }

        //if (ray.direction * normalHit > 0) return Color(-10.0, -10.0, -10.0)

        val v = material.generateVector(normalHit)
        val newRay = Ray(pointHit, v)

        // Recursively trace reflected light sources.
        val incoming = calculate(newRay, depth + 1)
        val cosTheta = v * normalHit

        val emittance = material.emittance
        val reflectance = material.reflectance
        val BRDF = reflectance / Math.PI

        //println(reflectance)

        val p = 1.0 / (2.0 * Math.PI)
        return emittance + BRDF * incoming * cosTheta / p // Divide to scale it down
    }
}