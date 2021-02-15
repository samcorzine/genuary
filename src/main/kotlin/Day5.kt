package genuary
import processing.core.PApplet
class GenuaryDay5() : PApplet() {
    override fun settings() {size(1000, 1000)}
    override fun draw() {(1..100).map{it.toFloat()*2281}.map{triangle(it%991,it%983,it%983,it%977,it%971,it%967)}}}
fun main(args: Array<String>) {PApplet.main("genuary.GenuaryDay5")}