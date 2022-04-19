package util

class Image (val width: Int, val height: Int) {

    var image: Array<Array<Color>> = Array(height) { Array(width) {Color(0.0, 0.0, 0.0)} }

    fun put(i: Int, j: Int, color: Color) {
        image[j][i] = color
    }

    operator fun get(i: Int, j: Int): Color {
        return image[j][i]
    }

    fun setBackground(color: Color) {
        for (j in 0 until height) {
            for (i in 0 until width) {
                put(i, j, color)
            }
        }
    }

    fun toArray(): Array<Array<Color>> {
        return image
    }
}