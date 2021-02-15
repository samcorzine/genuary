package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.lang.Math.random
import java.time.LocalDateTime
import kotlin.random.Random

fun PApplet.tri(trip: Triple<PVector, PVector, PVector>){
    triangle(trip.first.x, trip.first.y, trip.second.x, trip.second.y, trip.third.x, trip.third.y)
}

fun subd(trip: Triple<PVector, PVector, PVector>): List<Triple<PVector, PVector, PVector>>{
    val midpoint = PVector.mult(PVector.add(PVector.add(trip.first, trip.second), trip.third), 0.33333f + ((random().toFloat() - 0.5f)*0.2f))
    return listOf(
        Triple(trip.first, trip.second, midpoint),
        Triple(trip.second, trip.third, midpoint),
        Triple(trip.third, trip.first, midpoint)
    )
}

class GenuaryDay6() : PApplet() {
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
        var startTris = listOf(
            Triple(PVector(500f, 0f), PVector(1000f, 1000f), PVector(0f,1000f)),
            Triple(PVector(500f, 0f), PVector(0f, 0f), PVector(0f,1000f)),
            Triple(PVector(500f, 0f), PVector(1000f, 0f), PVector(1000f,1000f)),
        )
        fill(241f, 78f, 200f, 100f)
        for (i in 1..3){
            startTris = startTris.map{subd(it)}.flatten()
        }
        startTris.map{tri(it)}
        saveFrame("saves/day6-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay6")
}