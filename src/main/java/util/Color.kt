package util

// Convert to Kotlin
data class Color(var r: Double, var g: Double, var b: Double) {

    constructor(rp: Double, gp: Double, bg: Double, convert:Boolean = false) :
            this(rp/255.0, gp/255.0, bg/255.0)

    override fun toString(): String {
        return "$r $g $b"
    }

    // TODO edit clips
    fun clipTop(): Color {
        if (r > 255) r = 1.0
        if (g > 255) g = 1.0
        if (b > 255) b = 1.0
        return this
    }

    fun clipBottom(): Color {
        if (r < 0) r = 0.0
        if (g < 0) g = 0.0
        if (b < 0) b = 0.0
        return this
    }

    operator fun plus(c: Color) = Color(r + c.r, g + c.g, b + c.b)

    operator fun times(c: Color): Color {
        val rp = r * c.r
        val gp = g * c.g
        val bp = b * c.b
        return Color(rp, gp, bp)
    }

    operator fun times(n: Double) = Color(n * r, n * g, n * b)

    operator fun times(n: Int) = times(n.toDouble())

    operator fun div(n: Double) = Color(r / n, g / n, b / n)

    operator fun div(n: Int) = div(n.toDouble())

    operator fun plusAssign(c: Color) {
        r += c.r
        g += c.g
        b += c.b
    }

    override fun equals(c: Any?): Boolean {
        return if (c is Color) r == c.r && g == c.g && b == c.b
        else false
    }
}