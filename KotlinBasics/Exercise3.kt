package com.garmin.kotlinbasics

fun Int.factorial(): Long = when {
    this < 0 -> 0
    this == 0 || this == 1 -> 1
    else -> this * (this - 1).factorial()
}

fun main() {
    println(25.factorial())
}