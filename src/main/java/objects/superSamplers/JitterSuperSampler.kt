package objects.superSamplers

import util.Vector
import kotlin.random.Random

class JitterSuperSampler(numOfPoints: Int) : GridSuperSampler(numOfPoints) {

    override fun deviation(): Vector {
        val i = Random.nextDouble(-.5, .5)
        val j = Random.nextDouble(-.5, .5)
        var random = qpx * i - qpy * j
        return super.deviation() + random
    }
}