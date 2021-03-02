package com.garmin.garminkaptain

import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.data.UserReview
import java.util.*
import kotlin.random.Random

val firstNames = listOf("Michale", "Sarah", "Tom", "Jack", "Mary", "Rose", "Wilma", "Fred")
val lastNames = listOf("Jackson", "Flintstones", "Rubble", "Slate")
val titleExtra = listOf("Experience", "Adventure", "Escape", "Trip", "Tour", "Expedition")

fun generateUserReviews(reviewSummary: ReviewSummary): List<UserReview> {
    val reviews = mutableListOf<UserReview>()
    val date = Calendar.getInstance().time
    for (i in 1..reviewSummary.numberOfReviews) {
        reviews.add(
            generateUserReview(reviewSummary.poiId, date)
        )
    }
    return reviews
}

private fun generateUserReview(poiId: Long, date: Date) = UserReview(
    Random.nextLong(9999),
    poiId,
    Random.nextDouble(1.00, 5.00),
    generateString(),
    titleExtra.random(),
    "Dummy text",
    date
)

private fun generateString() = "${firstNames.random()} ${lastNames.random()}"