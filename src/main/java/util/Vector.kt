package util

import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

data class Vector(var x: Double, var y: Double, var z: Double) {

    constructor(theta: Double, phi: Double) : this(cos(phi) * cos(theta),cos(phi) * sin(theta), sin(phi))

    // magnitude
    val magnitudeSqr: Double
        get() = this * this

    val magnitude: Double
        get() = sqrt(magnitudeSqr)

    fun norm() = this / magnitude

    fun distSqr(b: Vector): Double {
        val dx = b.x - x
        val dy = b.y - y
        val dz = b.z - z
        return dx * dx + dy * dy + dz * dz
    }

    fun dist(b: Vector) = sqrt(distSqr(b))

    operator fun unaryMinus() = Vector(-x, -y, -z)

    operator fun plus(b: Vector) = Vector(x + b.x, y + b.y, z + b.z)

    operator fun minus(b: Vector) = Vector(x - b.x, y - b.y, z - b.z)

    // Dot product
    operator fun times(b: Vector) = x * b.x + y * b.y + z * b.z

    // Cross product
    operator fun rem(b: Vector): Vector {
        val xp = y * b.z - z * b.y
        val yp = z * b.x - x * b.z
        val zp = x * b.y - y * b.x
        return Vector(xp, yp, zp)
    }

    // multiply by constant
    operator fun times(c: Double) = Vector(c * x, c * y, c * z)

    operator fun times(c: Int) = this * c.toDouble()

    operator fun div(c: Double) = Vector(x / c, y / c, z / c)

    operator fun div(c: Int) = this / c.toDouble()

    operator fun plusAssign(b: Vector) {
        x += b.x
        y += b.y
        z += b.z
    }

    operator fun minusAssign(b: Vector) {
        x -= b.x
        y -= b.y
        z -= b.z
    }

    override fun equals(b: Any?): Boolean {
        return if (b is Vector) x == b.x && y == b.y && z == b.z
        else false
    }
}