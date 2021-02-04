package com.garmin.kotlinbasics

import java.util.*

data class HeartRateEntry(val date: Long, val value: Int)

fun populateData(vararg dataPair: Pair<Long, Int>): List<HeartRateEntry> = dataPair.map { HeartRateEntry(it.first, it.second) }

val data = populateData(
    1612310400L to 76,
    1612310400L to 89,
    1612310400L to 44,
    1612224000L to 47,
    1612224000L to 115,
    1612224000L to 76,
    1612224000L to 87,
    1612137600L to 90,
    1612137600L to 167,
)

fun main(){
    println("1. Minimum heart rate value = ${data.minByOrNull { it.value }?.value}")
    println("2. Average heart rate value = ${data.map { it.value }.average()}")
    println("3. Heart rate values above 100 = ${data.filter { it.value > 100 }.map { it.value }}")
    println("4. Heart rate values grouped = ${data.groupBy { Date(it.date) }.mapValues { entries -> entries.value.map { it.value } }}")
    println("5. Maximum heart rate per day = ${data.groupBy { Date(it.date) }.mapValues { entries -> entries.value.map { it.value }.maxByOrNull { it } }}")
}