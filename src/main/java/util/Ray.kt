package util


class Ray(val origin: Vector, val direction: Vector) {
    fun getPoint(t: Double): Vector {
        return origin + direction*t
    }
}
