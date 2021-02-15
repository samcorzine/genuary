package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime

class GenuaryDay1() : PApplet() {
    override fun settings() {
        size(1000, 1000)
    }

    override fun setup() {
        strokeCap(PConstants.PROJECT)
        strokeJoin(PConstants.MITER)
        frameRate(30f)
        ellipseMode(RADIUS)
        background(255)
        colorMode(HSB)
        noLoop()
    }

    override fun draw() {
        //(0,0) -> (1,10), (0,1) -> (2,10)
        background(255, 10f)
        stroke(10f, 10f, 10f, 100f)
        fill(40f, 80f, 120f, 50f)
        (1..1000 step 50).map { i ->
            (1..5).map { j ->
                (1..100 step 50).map{ k ->
                    fill(k.toFloat()/10f, i.toFloat()/10f, 0f, 0f)
                    val noise = random(0f, 1f)
                    triangle(500f, 100f, i.toFloat() * noise, 600f + (k*j), i.toFloat() * noise, 600f)

                }
            }
        }
//        saveFrame("saves/day1-${LocalDateTime.now()}.png")

        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay1")
}