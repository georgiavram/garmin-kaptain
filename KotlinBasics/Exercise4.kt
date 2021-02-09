package com.garmin.kotlinbasics

data class Rectangle(val x: Int, val y: Int, val w: Int, val h: Int)

class Paint {
    var color: Long = 0x00FF00
    var strokeWidth: Int = 5
    fun drawRectangle(rect: Rectangle) {
        println("Drawing $rect color: $color stroke: $strokeWidth")
    }
}

fun render(paint: Paint?, rectangles: List<Rectangle?>) {
    paint?.apply {
        color = 0xFF0000
        rectangles.forEach { rect -> rect?.let { drawRectangle(it) } }
    }
}