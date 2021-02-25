package com.garmin.garminkaptain.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.garmin.garminkaptain.generateUserReviews
import java.util.*

@Entity(tableName = "poi_table")
data class PointOfInterest(
    @PrimaryKey val id: Long,
    @Embedded val mapLocation: MapLocation,
    val name: String,
    val poiType: String,
    @Embedded val reviewSummary: ReviewSummary,
    @Ignore var userReviews: List<UserReview>
) {
    constructor(id: Long, mapLocation: MapLocation, name: String, poiType: String, reviewSummary: ReviewSummary) : this(
        id,
        mapLocation,
        name,
        poiType,
        reviewSummary,
        listOf()
    )

    init {
        if (reviewSummary.numberOfReviews > 0) {
            userReviews = generateUserReviews(this)
            reviewSummary.averageRating = userReviews.map { it.rating }.average()
        } else {
            userReviews = listOf()
        }
    }
}

data class MapLocation(
    val latitude: Double,
    val longitude: Double
)

data class ReviewSummary(
    var averageRating: Double,
    var numberOfReviews: Int
)

data class UserReview(
    val id: Long,
    val rating: Double,
    val username: String,
    val title: String,
    val review: String,
    val date: Date
)