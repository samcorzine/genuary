package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime

class GenuaryDay8() : PApplet() {
    override fun settings() {
        size(1000, 1000)
    }
    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(2f)
        ellipseMode(RADIUS)
        background(255f)
        colorMode(HSB, 360f)
        noLoop()
        shapeMode(CENTER)
    }
    override fun draw() {
//        Curves only
        val flowField = (0..2000).map{mutableListOf(PVector(1f, 0f))}
        for (y in 0..2000){
            for (x in 0..2000) {
                val momentum = 0.995f
                val randVec = PVector.random2D()
                flowField[y].add(
                    PVector.add(
                        PVector.mult(flowField[y][x], momentum),
                        PVector.mult(randVec, 1f - momentum))
                )
            }
        }
        (0..100).map {x ->
            (0..100).map { y ->
                beginShape();
                var currentVertex = PVector((x * 10f) - 50, y * 10f)
                for (iter in 0..10) {
                    curveVertex(currentVertex.x, currentVertex.y)
                    currentVertex = PVector.add(
                        currentVertex,
                        PVector.mult(flowField[floor(currentVertex.y) + 500][floor(currentVertex.x) + 500], 500f)
                    )
                }
                endShape()
            }
        }
        saveFrame("saves/day8-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}


fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay8")
}