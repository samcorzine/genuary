package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime


operator fun PVector.plus(vec2: PVector): PVector{
    return PVector.add(this, vec2)
}
//randomness increases along the yaxis
class GenuaryDay19() : PApplet() {
    override fun settings() {
        size(1000, 1000)
    }
    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(2f)
        ellipseMode(RADIUS)
        background(255)
        colorMode(RGB, 255f)
//        noLoop()
        shapeMode(CENTER)
        noFill()
    }
    override fun draw() {
        background(255f)
        stroke(0f)
        val field = {vec:PVector -> random(-0.5f, 0.5f) * vec.y}
        for (i in 0..1000 step 10) {
            val seq = generateSequence((PVector(i.toFloat(), -5f))) { it + PVector(0f, 5f) }
            beginShape()
            seq
                .map{it + PVector(field(it), 0f)}
                .take(2000)
                .forEach{curveVertex(it.x, it.y)}
            endShape()
        }
        saveFrame("saves/day19-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay19")
}