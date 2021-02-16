package com.garmin.garminkaptain

import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.UserReview
import java.util.*
import kotlin.random.Random

val firstNames = listOf("Michale", "Sarah", "Tom", "Jack", "Mary", "Rose", "Wilma", "Fred")
val lastNames = listOf("Jackson", "Flintstones", "Rubble", "Slate")
val titleExtra = listOf("Experience", "Adventure", "Escape", "Trip", "Tour", "Expedition")

fun generateUserReviews(poi: PointOfInterest): List<UserReview> {
    val reviews = mutableListOf<UserReview>()
    val number = poi.reviewSummary.numberOfReviews
    val date = Calendar.getInstance().time
    for (i in 1..number) {
        reviews.add(
            UserReview(
                Random.nextLong(9999),
                Random.nextDouble(1.00, 5.00),
                "${firstNames.random()} ${lastNames.random()}",
                "${poi.name} ${titleExtra.random()}",
                "Dummy text",
                date
            )
        )
    }
    return reviews
}