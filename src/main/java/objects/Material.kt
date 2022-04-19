package objects

import util.Color
import util.Vector
import kotlin.math.asin
import kotlin.math.sign
import kotlin.random.Random

class Material {
    var emittance: Color
    var reflectance: Color

    constructor(reflectance: Color) {
        emittance = Color(.0, .0, .0)
        this.reflectance = reflectance
    }

    constructor(reflectance: Color, emittance: Color, strength: Double) {
        this.emittance = emittance * strength
        this.reflectance = reflectance
    }

    // TODO: replace with BRDF
    fun generateVector(normal: Vector): Vector {
        val u = Random.nextDouble()
        val v = Random.nextDouble()
        val t = 2 * Math.PI * u
        val p = asin(2 * v * v - 1)
        val V = Vector(t, p)
        return V * sign(V * normal)
    }

    companion object {
        private const val BIAS = 0.001
        private const val airRefractionIndex = 1.0
    }
}