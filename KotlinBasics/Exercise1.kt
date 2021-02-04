package com.garmin.kotlinbasics

import kotlin.random.Random

fun main() {
    getRandomBloodPressure().let {
        println("systolic = ${it.first}, diastolic = ${it.second}")
        println(getBloodPressureCategory(it.first, it.second).interpretation)
    }
}

private fun getRandomBloodPressure() = Pair(Random.nextInt(80, 200), Random.nextInt(50, 150))

private fun getBloodPressureCategory(systolic: Int, diastolic: Int): BloodPressureCategory = when {
    systolic < 120 && diastolic < 80 -> BloodPressureCategory.NORMAL
    systolic in 120..129 && diastolic < 80 -> BloodPressureCategory.ELEVATED
    systolic in 130..139 || diastolic in 80..89 -> BloodPressureCategory.HIGH_BLOOD_PRESSURE_1
    systolic >= 140 && diastolic <= 120 || systolic <= 180 && diastolic >= 90 -> BloodPressureCategory.HIGH_BLOOD_PRESSURE_2
    else -> BloodPressureCategory.HYPERTENSIVE_CRISIS
}

enum class BloodPressureCategory {
    NORMAL,
    ELEVATED,
    HIGH_BLOOD_PRESSURE_1,
    HIGH_BLOOD_PRESSURE_2,
    HYPERTENSIVE_CRISIS;

    val interpretation: String
        get() = when (this) {
            NORMAL -> "Your blood pressure is normal!"
            ELEVATED -> "Your blood pressure is elevated!"
            HIGH_BLOOD_PRESSURE_1 -> "Your blood pressure is high stage 1!"
            HIGH_BLOOD_PRESSURE_2 -> "Your blood pressure is high stage 2!"
            HYPERTENSIVE_CRISIS -> "Your blood pressure is very high! Consult your doctor immediately!"
        }
}