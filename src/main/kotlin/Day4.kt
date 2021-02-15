package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime
import kotlin.random.Random


class GenuaryDay4() : PApplet() {
    fun drawpattern(){
        val b = 500f
        ellipse(b, 1000f - b, 50f, 50f)
        (0..200)
            .map{it.toFloat()}
            .map{t ->
                val it = t* sin(t)
                pushMatrix()
                translate(it * 10, 0f)
                rotate(it)
                ellipse(b, 1000f - b, 50f, 50f)
                popMatrix()
            }
    }

    override fun settings() {
        size(1000, 1000)
    }

    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(2f)
        ellipseMode(RADIUS)
        background(255)
        colorMode(HSB)
        noStroke()
        noLoop()
        shapeMode(CENTER)
    }

    override fun draw() {
        //(0,0) -> (1,10), (0,1) -> (2,10)
//        background(255, 10f)
//        stroke(10f, 10f, 10f, 100f)
        (0..3).map { tiptop ->
            pushMatrix()
            translate(tiptop.toFloat() * 25, 0f)
            (0..3).map { top ->
                pushMatrix()
                rotate(top.toFloat() / 2)
                (0..5).map {
                    fill(140f + (20 * it), 150f, 200f, 20f)
                    pushMatrix()
                    translate(0f, it.toFloat() * 30f)
                    drawpattern()
                    popMatrix()
                }
                popMatrix()
            }
        }
        saveFrame("saves/day4-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay4")
}