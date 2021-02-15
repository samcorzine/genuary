package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.lang.Double.min
import java.time.LocalDateTime
//fun PApplet.drawline(y_coor: Float, field: (PVector) -> Float){
//    var wasPositive = false
//    var lasty = y_coor
//    for (x in 0..1000) {
//        val point = PVector(x.toFloat(), lasty)
//        val fieldIntensity = field(point)
//        if (fieldIntensity > 0f){
//            if (wasPositive == false) {
//                beginShape()
//                wasPositive = true
//            }
//            curveVertex(point.x, point.y)
//        }
//        if (fieldIntensity <= 0f){
//            if (wasPositive == true) {
//                if (random(1f) > 0.99f) {
//                    endShape()
//                    wasPositive = false
//                }
//            }
//        }
//        lasty = lasty + listOf(-7f, -2f,2f,0f, 0f, 0f, 7f).random()
//    }
//    endShape()
//}


fun PApplet.drawMaskCurveParametric(curveFun: (Float) -> PVector, domain: Iterable<Float>, field: (PVector) -> Float) {
    var wasPositive = false
    for (t in domain){
        val point = curveFun(t)
        val fieldIntensity = field(point)
        if (fieldIntensity > 0f){
            if (wasPositive == false) {
                beginShape()
            }
            wasPositive = true
            curveVertex(point.x, point.y)
        }
        if (fieldIntensity <= 0f){
            if (wasPositive == true) {
                endShape()
            }
            wasPositive = false
        }
    }
    endShape()
}

fun PApplet.drawNoiseCurveParametric(curveFun: (Float) -> PVector, domain: Iterable<Float>, field: (PVector) -> Float) {
    beginShape()
    for (t in domain){
        val point = curveFun(t)
        val fieldIntensity = field(point)
        val noise_val = kotlin.math.min(fieldIntensity, 0f)
        val plotPoint = PVector.add(PVector.mult(PVector.random2D(), noise_val), point)
        curveVertex(plotPoint.x, plotPoint.y)
    }
    endShape()
}



class GenuaryDay9Alt() : PApplet() {
    override fun settings() {
        size(1000, 1000);
    }
    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(1f)
//        ellipseMode(RADIUS)
        background(255)
        colorMode(HSB, 360f)
//        shapeMode(CENTER)
//        noLoop()
        noFill()
    }
    override fun draw() {
        background(360)
        val center = PVector(this.width.toFloat() /2, this.height.toFloat()/2)
//        val theField = {v: PVector -> if(v.x > 200f && v.x < 800f && v.y > 200f && v.y < 800f) 1f else -1f}
//        val theField = {v: PVector -> 10f * cos(v.y / 50)}
        val diffVec = PVector(250f * sin(frameCount / 50f), -250f * sin(frameCount / 50f))
        val diffVectwo = PVector(50f * sin(frameCount / 20f), 70f * sin(frameCount / 70f))
        val theField = listOf(
            ball(center, 50f),
            ball(PVector.add(center, diffVec), 5f),
            ball(PVector.add(center, diffVectwo), 10f),
        ).reduce{first, second -> {vec -> 5 * first(vec) + second(vec)}}
//        val hue = 10f
//        stroke(hue, 300f, 360f)
        for (offset in 0..20 step 1){
            drawNoiseCurveParametric(
                {t -> PVector(10f + (50f * offset), t)},
                (0..1000).map{it.toFloat()},
                theField
            )
        }
        for (offset in 0..20 step 1){
            drawMaskCurveParametric(
                {t -> PVector(35f + (50f * offset), t)},
                (0..1000).map{it.toFloat()},
                theField
            )
        }
//        val theField2 = {v: PVector -> -1 * (v.x % 100 - 50)}
//        val hue = 10f
//        stroke(hue, 300f, 360f)
//        for (offset in 0..101){
//            drawMaskCurveParametric(
//                {t -> PVector(sin(t/500) * 1000, t + (10 * offset))},
//                (0..1000).map{it.toFloat()},
//                theField2
//            )
//        }
//        fill(hue, 300f, 300f, 80f)
//        rect(700f,200f, 200f, 200f)
//        saveFrame("day9alt/${LocalDateTime.now()}.png")
        println(frameCount)
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay9Alt")
}