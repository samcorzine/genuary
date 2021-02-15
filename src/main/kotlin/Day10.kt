package genuary

import processing.core.PApplet
import processing.core.PConstants
import processing.core.PVector
import java.time.LocalDateTime
import kotlin.math.floor
import kotlin.math.log10
import kotlin.random.Random

data class Node(val pos: PVector, val children: List<Node>)

fun PApplet.drawTree(node: Node){
    val children = node.children
    children.map{
        stroke(0f)
        line(node.pos.x, node.pos.y, it.pos.x, it.pos.y)
        fill(111f, 68f * 2.5f, 53f * 2.5f, 50f)
        stroke(111f, 68f * 2.5f, 53f * 2.5f, 50f)
        ellipse(it.pos.x, it.pos.y, 20f, 20f)
    }
    children.map{drawTree(it)}
}

fun scaledRandom(depth: Int, maxDepth: Int, maxSize: Int): PVector {
    return PVector(
        floor((Random.nextFloat() - 0.5f) * (maxSize/depth)),
        -1 * floor(Random.nextFloat() * (maxSize/depth))
    )
}

fun children(pos: PVector, depth: Int, maxDepth: Int, maxSize: Int): List<Node>{
    if (depth >= maxDepth){return listOf()}
    val numKids = Random.nextInt()%5 + 2
    return (0..numKids).map{
        val newPos = PVector.add(pos, scaledRandom(depth, maxDepth, maxSize))
        Node(newPos, children(newPos, depth+1, maxDepth, maxSize))
    }
}

class GenuaryDay10() : PApplet() {
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
        noLoop()
        shapeMode(CENTER)
    }
    override fun draw() {
        (0..1000 step 3)
            .map{PVector(it.toFloat(), 0f)}
            .map{(0..800 step 3).map{y->PVector.add(it, PVector(0f, y.toFloat()))}}
            .flatten()
            .map{
                fill(206f, 150f, 150f, 55f)
                stroke(206f, 150f, 150f, 5f)
                ellipse(it.x, it.y, random(5f), random(5f))
            }
        (0..1000 step 3)
            .map{PVector(it.toFloat(), 0f)}
            .map{(800..1000 step 3).map{y->PVector.add(it, PVector(0f, y.toFloat()))}}
            .flatten()
            .map{
                fill(31f, 360f, 38f * 3.6f, 55f)
                stroke(31f, 360f, 38f * 3.6f, 5f)
                ellipse(it.x, it.y, random(5f), random(5f))
            }
        val rootPos = PVector(800f, 800f)

        val tree = Node(
            rootPos,
            listOf(Node(PVector(750f, 600f),children(PVector(750f, 600f), 1, 7, 200)))
        )
        drawTree(tree)
        saveFrame("saves/day10-${LocalDateTime.now()}.png")
        kotlin.io.println("done")
    }
}

fun main(args: Array<String>) {
    PApplet.main("genuary.GenuaryDay10")
}