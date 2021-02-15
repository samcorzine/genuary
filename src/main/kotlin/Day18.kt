package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime
import kotlin.random.Random

fun PVector.rightSeq(): Sequence<PVector>{
    return generateSequence(PVector(this.x, this.y)) {it + PVector(Random.nextFloat() * 20 + 10, 0f)}
}
fun PVector.upSeq(): Sequence<PVector>{
    return generateSequence(PVector(this.x, this.y)) {it + PVector(0f, Random.nextFloat() * -20 - 10)}
}

fun PApplet.legs(node: PVector, depth: Int){
    node
        .rightSeq()
        .windowed(2)
        .take(5)
        .forEach{
            if(depth > 0 && Random.nextBoolean()){
                legs(it[1], depth-1)
            }
            if(random(0f, 1f) > 0.5f){
                line(it[0].x, it[0].y, it[1].x, it[1].y)
            }
        }
    node
        .upSeq()
        .windowed(2)
        .take(5)
        .forEach{
            if(depth > 0 && Random.nextBoolean()){
                legs(it[1], depth-1)
            }
            if (random(0f, 1f) > 0.5f){
                line(it[0].x, it[0].y, it[1].x, it[1].y)

            }
        }
}


class GenuaryDay18() : PApplet() {
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
        noLoop()
        shapeMode(CENTER)
        noFill()
    }
    override fun draw() {
        background(255f)
        stroke(0f)
        val root = PVector(0f,1000f)
        legs(root, 10)
    //        saveFrame("saves/day18-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay18")
}