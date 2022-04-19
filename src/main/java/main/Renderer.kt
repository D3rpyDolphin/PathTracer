package main

import main.integrators.Integrator
import util.Color
import util.Image

class Renderer(private val scene: Scene, private val integrator: Integrator) {

    val images = Array(scene.cameras.size) { i -> Image(scene.cameras[i].width, scene.cameras[i].height) }

    fun render(samples: Int) {
        for (c in 0 until scene.cameras.size) {
            val camera = scene.cameras[c]
            val image = images[c]
            for (j in 0 until image.height) {
                println("${j * 100 / image.height}%")
                for (i in 0 until image.width) {
                    val color = Color(.0, .0, .0)

                    for (s in 1..samples) {
                        color += integrator.calculate(camera.getPrimaryRay(i, j)) / samples.toDouble()
                    }

                    image.put(i, j, color)
                }
            }
        }
    }

    /*fun render(samples: Int, x: Int, y: Int, camera: Camera) = runBlocking {
        var widthRatio = camera.width / x
        var heightRatio = camera.height / y

        // Iterate through blocks
        for (blockY in 0 until y) {
            for (blockX in 0 until x) {
                launch {
                    val startX = blockX * widthRatio
                    val startY = blockY * heightRatio
                    val stopX = (blockX+1) * widthRatio
                    val stopY = (blockY+1) * heightRatio
                    runBlock(samples, startX, stopX, startY, stopY, camera)
                }
            }
        }
    }

    private fun runBlock(samples: Int, startX: Int, stopX: Int, startY: Int, stopY: Int, camera: Camera) {
        for (j in startY until stopY) {
            for (i in startX until stopX) {
                val primaryRay = camera.getPrimaryRay(i, j)
                val nearestIntersection = scene.nearest(primaryRay)
                val color = Color(.0, .0, .0)
                for (s in 1..samples) {
                    val test = pathTracing(primaryRay, 0, nearestIntersection)
                    color += test / samples.toDouble()
                }
                image.put(i, j, color)
            }
        }
    }*/
}