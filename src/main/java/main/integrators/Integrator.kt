package main.integrators

import util.Color
import util.Ray

interface Integrator {
    fun calculate(ray: Ray, depth: Int = 0): Color
}