package com.garmin.garminkaptain.data

import androidx.room.*
import com.garmin.garminkaptain.generateUserReviews
import java.util.*

@Entity(tableName = "poi_table")
data class PointOfInterest(
    @PrimaryKey val id: Long,
    @Embedded val mapLocation: MapLocation,
    val name: String,
    val poiType: String,
    @Embedded val reviewSummary: ReviewSummary
)

data class MapLocation(
    val latitude: Double,
    val longitude: Double
)

data class ReviewSummary(
    var averageRating: Double,
    var numberOfReviews: Int
)

@Entity(tableName = "user_review")
data class UserReview(
    @PrimaryKey val id: Long,
    val poiId: Long,
    val rating: Double,
    val username: String,
    val title: String,
    val review: String,
    val date: Date
)

data class PoiWithReviews(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val list: List<UserReview>
)