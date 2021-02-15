package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime
import kotlin.random.Random

fun rule30(left: Boolean, center: Boolean, right: Boolean): Boolean {
    return mapOf(
         Triple(true, true, true) to false,
         Triple(true, true, false) to false,
         Triple(true, false, true) to  false,
         Triple(true, false, false) to true,
         Triple(false, true, true) to true,
         Triple(false, true, false) to true,
         Triple(false, false, true) to true,
         Triple(false, false, false) to false
    )[Triple(left, center, right)]!!
}

class GenuaryDay2() : PApplet() {
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
//        noLoop()
    }

    override fun draw() {
        //(0,0) -> (1,10), (0,1) -> (2,10)
//        background(255, 10f)
//        stroke(10f, 10f, 10f, 100f)
        fill(10f, 150f, 150f, 100f)
        val prec = 100
        val grid = mutableListOf((0..prec).map{ x -> Random.nextBoolean()})
        for (y in 1..prec){
            val newRow = (0..prec+1).map{ x ->
                val lastRow = grid[y-1]
                rule30(
                    lastRow.getOrElse(x-1, {num -> true}),
                    lastRow.getOrElse(x, {num -> true}),
                    lastRow.getOrElse(x+1, {num ->  true})
                )
            }
            grid.add(newRow)
        }
        val outputs = (0..prec).flatMap{ x ->
            (0..prec).map{ y ->
                Pair(PVector.mult(PVector(x.toFloat(), y.toFloat()), 10f + random(0f,0.1f)), grid[y][x])
            }
        }
        outputs.map{ (xy, b) ->
            if (b) {triangle(xy.x, xy.y , xy.x + 10f, xy.y, xy.x + 10f, xy.y + 10f)}
            else{triangle(xy.x, xy.y , xy.x, xy.y + 10f, xy.x + 10f, xy.y + 10f)}
        }


//            .flatMap{x -> (0..100).map{
//                PVector.mult(PVector(x.toFloat(), it.toFloat()), 10f)
//            }}
//            .forEach{
//                    (xy, b) ->
//                triangle(xy.x, xy.y , xy.x + 10f, xy.y, xy.x + 10f, xy.y + 10f)
//            }

        saveFrame("saves/day2-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay2")
}