package com.garmin.garminkaptain.data

import androidx.room.*
import java.util.*

@Entity(tableName = "poi_table")
data class PointOfInterest(
    @PrimaryKey val id: Long,
    val name: String,
    val poiType: String,
)


@Entity(
    tableName = "map_location",
    foreignKeys = [ForeignKey(entity = PointOfInterest::class, parentColumns = ["id"], childColumns = ["poiId"], onDelete = ForeignKey.CASCADE)]
)
data class MapLocation(
    @PrimaryKey(autoGenerate = true) val locationId: Long,
    var poiId: Long,
    val latitude: Double,
    val longitude: Double
) {
    constructor(poiId: Long, latitude: Double, longitude: Double) : this(0, poiId, latitude, longitude)
}

@Entity(
    tableName = "review_summary",
    foreignKeys = [ForeignKey(entity = PointOfInterest::class, parentColumns = ["id"], childColumns = ["poiId"], onDelete = ForeignKey.CASCADE)]
)
data class ReviewSummary(
    @PrimaryKey(autoGenerate = true) val summaryId: Long,
    var poiId: Long,
    var averageRating: Double,
    var numberOfReviews: Int
) {
    constructor(poiId: Long, averageRating: Double, numberOfReviews: Int) : this(0, poiId, averageRating, numberOfReviews)
}

@Entity(
    tableName = "user_review",
    foreignKeys = [ForeignKey(entity = PointOfInterest::class, parentColumns = ["id"], childColumns = ["poiId"], onDelete = ForeignKey.CASCADE)]
)
data class UserReview(
    @PrimaryKey val id: Long,
    val poiId: Long,
    val rating: Double,
    val username: String,
    val title: String,
    val review: String,
    val date: Date
)

data class PoiDTO(
    @Embedded val poi: PointOfInterest,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val list: List<UserReview>,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId"
    )
    val mapLocation: MapLocation,
    @Relation(
        parentColumn = "id",
        entityColumn = "poiId",

        )
    val reviewSummary: ReviewSummary
)