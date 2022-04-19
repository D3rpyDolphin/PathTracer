package objects.superSamplers

import util.Vector

interface SuperSampler {

    fun init(qx: Vector, qy: Vector)

    fun deviation(): Vector

}