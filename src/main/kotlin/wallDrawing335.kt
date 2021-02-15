package genuary

import processing.core.PApplet
import processing.core.PVector
import java.time.LocalDateTime

fun PApplet.drawMaskCurveParametric(curveFun: (Float) -> PVector, domain: Iterable<Float>, field: (PVector) -> Boolean) {
    var wasTrue = false
    for (t in domain){
        val point = curveFun(t)
        val fieldVal = field(point)
        if (fieldVal){
            if (!wasTrue) {
                beginShape()
            }
            wasTrue = true
            curveVertex(point.x, point.y)
        }
        if (!fieldVal){
            if (wasTrue) {
                endShape()
            }
            wasTrue = false
        }
    }
    endShape()
}

class WallDrawing335() : PApplet() {
    override fun settings() {
        size(1000, 1000);
    }
    override fun setup() {
        frameRate(1f)
        background(255)
        colorMode(HSB, 360f)
        noFill()
        noLoop()
    }
    override fun draw() {
        background(360)
        val circleField = {v: PVector -> 150f > PVector.dist(v, PVector(700f, 500f)) }
        val squareField = {v: PVector -> v.x > 175f && v.x < 425f && v.y >= 375f && v.y <= 625f}
        val notField = {v: PVector -> !circleField(v) && !squareField(v)}
        for (offset in 0..200 step 1){
            drawMaskCurveParametric(
                {t -> PVector(t, 5f * offset)},
                (0..1000).map{it.toFloat()},
                circleField
            )
            drawMaskCurveParametric(
                {t -> PVector(t, 5f * offset)},
                (0..1000).map{it.toFloat()},
                squareField
            )
            drawMaskCurveParametric(
                {t -> PVector(5f * offset, t)},
                (0..1000).map{it.toFloat()},
                notField
            )
        }
        saveFrame("335/${LocalDateTime.now()}.png")

    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.WallDrawing335")
}