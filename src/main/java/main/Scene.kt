package main

import objects.Camera
import objects.lights.Light
import objects.renderableObjects.RenderableObj
import util.Ray

class Scene(val cameras: Array<Camera>, val objects: Array<RenderableObj>, val lights: Array<Light>) {
    constructor(camera: Camera, objects: Array<RenderableObj>, lights: Array<Light>) : this(arrayOf(camera), objects, lights)

    init {
        // TODO: Partition scene using BHV as optimization
        partitionScene()
    }

    fun nearest(ray: Ray): RenderableObj.IntersectionResult? {
        var minDistance = Double.MAX_VALUE
        var nearestIntersection: RenderableObj.IntersectionResult? = null
        for (obj in objects + lights) {
            val intersectionResults = obj.intersect(ray)
            intersectionResults?.let {
                val distance = ray.origin.dist(intersectionResults.pointHit)
                if (distance < minDistance) {
                    nearestIntersection = intersectionResults
                    minDistance = distance
                }
            }
        }
        //        for (Light l : lights) {
//            IntersectionResults intersectionResults = l.intersect(ray);
//            boolean hit = intersectionResults.isHit();
//            Vector pHit = intersectionResults.getPointHit();
//            Vector nHit = intersectionResults.getNormalHit();
//            if (hit) {
//                double distance = dist(ray.getOrigin(), pHit);
//                if (distance < minDistance) {
//                    nearestIntersection = new nearestIntersection(true, l, pHit, nHit);
//                    // Update min distance
//                    minDistance = distance;
//                }
//            }
//        }
        return nearestIntersection
    }

    private fun inShadow(shadowRay: Ray): Boolean {
        // Checks to see if object in the way of ray/light
        for (obj in objects) {
            obj.intersect(shadowRay)?.let {
                return true
            }
        }
        return false
    }

    fun partitionScene() {}
}