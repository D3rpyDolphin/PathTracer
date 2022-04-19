package util

import objects.renderableObjects.Triangle
import java.lang.Math.toRadians
import kotlin.math.cos
import kotlin.math.sin

class Rotation (val xRot: Double, val yRot: Double, val zRot: Double) {
    enum class Axis {
        X, Y, Z
    }

    private class Matrix(val vX: Vector, val vY: Vector, val vZ: Vector) {
        fun matmul(vector: Vector): Vector {
            return vX * vector.x + vY * vector.y + vZ * vector.z
        }
    }
    
    private val gamma = toRadians(xRot)
    private val beta = toRadians(yRot)
    private val alpha = toRadians(zRot)

    private val matrix = Matrix(getRotatedAxis(Axis.X), getRotatedAxis(Axis.Y), getRotatedAxis(Axis.Z))
    
    fun getRotatedAxis(axis: Axis): Vector {
        return when (axis) {
            Axis.X -> Vector(cos(alpha)*cos(beta),
                -sin(alpha)*cos(beta),
                sin(beta))
            Axis.Y -> Vector(cos(alpha)*sin(beta)*sin(gamma) + sin(alpha)*cos(gamma),
                -sin(alpha)*sin(beta)*sin(gamma) + cos(alpha)*cos(gamma),
                -cos(beta)*sin(gamma))
            Axis.Z -> Vector(-cos(alpha)*sin(beta)*cos(gamma) + sin(alpha)*sin(gamma),
                sin(alpha)*sin(beta)*cos(gamma) + cos(alpha)*sin(gamma),
                cos(beta)*cos(gamma))
        }
    }

    fun rotatePoint(point: Vector, origin: Vector = Vector(.0, .0, .0)): Vector {
        return matrix.matmul(point-origin) + origin
    }
    
    operator fun times(n: Double) = Rotation(n * alpha, n * beta, n * gamma)

    operator fun plus(r: Rotation): Rotation = Rotation(xRot + r.xRot, yRot + r.yRot, zRot + r.zRot)

    operator fun minus(r: Rotation): Rotation = Rotation(xRot - r.xRot, yRot - r.yRot, zRot - r.zRot)
}