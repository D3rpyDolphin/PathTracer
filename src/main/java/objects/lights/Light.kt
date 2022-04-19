package objects.lights

import objects.renderableObjects.RenderableObj
import util.Vector

interface Light : RenderableObj {
    fun getRandomPoint(): Vector
}