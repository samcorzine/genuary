package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import kotlin.math.sin


//Prompt: Interference Patterns

fun ball(center: PVector, period: Float): (PVector) -> Float {
    return {vec ->
        val dist = PVector.dist(center, vec)
        sin(dist/period)}
}

fun PApplet.drawhorizontalline(y_coor: Float, field: (PVector) -> Float){
    var wasPositive = false
    for (x in 0..1000) {
        val point = PVector(x.toFloat(), y_coor)
        val fieldIntensity = field(point)
        if (fieldIntensity > 0f){
            if (wasPositive == false) {
                beginShape()
                wasPositive = true
            }
//            val y_noise = if(x%2 == 0){randomGaussian() * 10}else{0f}
            curveVertex(point.x, point.y)
        }
        if (fieldIntensity <= 0f){
            if (wasPositive == true) {
                endShape()
                wasPositive = false
            }
        }
    }
    endShape()
}

fun fieldSum(field1: (PVector) -> Float, field2: (PVector) -> Float): (PVector) -> Float{
    return {vec -> field1(vec) + field2(vec)}
}

class GenuaryDay9() : PApplet() {
    override fun settings() {
        size(1000, 1000)
    }
    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(2f)
        ellipseMode(RADIUS)
        background(255)
        colorMode(HSB, 360f)
        shapeMode(CENTER)
    }
    override fun draw() {
        background(360)
        val center = PVector(500f, 500f)
        val diffVec = PVector(250f * sin(frameCount / 50f), -250f * sin(frameCount / 50f))
        val diffVectwo = PVector(50f * sin(frameCount / 20f), 70f * sin(frameCount / 70f))
        val theField = listOf(
            ball(center, 50f),
            ball(PVector.add(center, diffVec), 5f),
            ball(PVector.add(center, diffVectwo), 10f),
        ).reduce{first, second -> fieldSum(first, second)}
        (0..1000 step 5)
            .map{it.toFloat()}
            .map{drawhorizontalline(it, theField)}
        saveFrame("day8render/$frameCount.png")
        println(frameCount)
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay9")
}