package main

import main.integrators.PathTracer
import objects.Material
import objects.Camera
import objects.lights.AreaLight
import objects.renderableObjects.*
import util.Color
import util.DisplayUtil
import util.Rotation
import util.Vector

fun main(args: Array<String>) {
    val white = Material(Color(255.0, 255.0, 255.0, true))
    val green = Material(Color(0.0, 255.0, .0, true))
    val red = Material(Color(255.0, 0.0, .0, true))

    val stage = RectangularPrism(Vector(0.0, 2.5, 0.0), 5.0, 5.0, 5.0,
        materials = arrayOf(white, white, green, red, white, white))
    stage.invertNormals()

    //val sphere = Sphere(0.0, 1.0, 0.0, 1.0, white)

    val leftBox = RectangularPrism(Vector(-1.0, 1.5, 1.0), 1.0, 3.0, 1.0,
        Rotation(0.0, -30.0, 0.0), white)
    val rightBox = RectangularPrism(Vector(0.8, .45, 0.0), 0.9, 0.9, 0.9,
        Rotation(0.0, 30.0, 0.0), white)

    val light = AreaLight(Vector(0.0, 4.99, 0.0), 5.0, 5.0,
        Rotation(180.0, .0, .0), 5, 5, Color(255.0, 255.0, 255.0, true), 0.9)

    val camera1 = Camera(Vector(0.0, 2.5, -2.0), Vector(0.0, 2.5, 0.0), Camera.Orientation.UP,
        720, 720, 100.0)
    //val camera2 = Camera(Vector(-5.0, 1.0, .0), Vector(0.0, 0.0, 0.0), Camera.Orientation.UP,
    // 1080, 720, 70.0, GridSuperSampler(5))
    //val camera3 = Camera(Vector(-5.0, 1.0, .0), Vector(0.0, 0.0, 0.0), Camera.Orientation.UP,
    // 1080, 720, 70.0, JitterSuperSampler(5))

    val scene = Scene(arrayOf(camera1), arrayOf(stage, leftBox, rightBox), arrayOf(light))

    val integrator = PathTracer(scene, 5)
    val renderer = Renderer(scene, integrator)

    renderer.render(10)

    DisplayUtil.show(renderer.images[0])
}