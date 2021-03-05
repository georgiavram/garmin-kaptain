package com.garmin.garminkaptain.data

import android.util.LongSparseArray
import com.garmin.garminkaptain.generateUserReviews
import com.garmin.garminkaptain.model.MapBoundingBox
import kotlin.random.Random

val poiList: List<PointOfInterest> = listOf(
    PointOfInterest(
        46067,
        "Point Bonita",
        "Anchorage"
    ),
    PointOfInterest(
        12975,
        "Richardson Bay Marina",
        "Marina"
    ),
    PointOfInterest(
        46085,
        "Needles",
        "Anchorage"
    ),
    PointOfInterest(
        19637,
        "Golden Gate Bridge",
        "Bridge"
    ),
    PointOfInterest(
        60928,
        "Horseshoe Cove",
        "Anchorage"
    ),
    PointOfInterest(
        39252,
        "Presidio Yacht Club",
        "Marina"
    ),
    PointOfInterest(
        25644,
        "Ayala Cove",
        "Anchorage"
    ),
    PointOfInterest(
        61865,
        "Tide Rips",
        "Hazard"
    ),
    PointOfInterest(
        46713,
        "Dangerous Rock",
        "Hazard"
    ),
    PointOfInterest(
        57109,
        "Woodrum Marine Boat Repair/Carpentry",
        "Business"
    )
)

val mapLocations = mutableListOf<MapLocation>().also { list ->
    list.add(MapLocation(46067, 37.8180564724432, -122.52704143524173))
    list.add(MapLocation(12975, 37.8770892291283, -122.503309249878))
    list.add(MapLocation(46085, 37.82878469060811, -122.47633210712522))
    list.add(MapLocation(60928, 37.8325155338083, -122.47500389814363))
    list.add(MapLocation(39252, 37.833886767314, -122.475371360779))
    list.add(MapLocation(25644, 37.8673327691044, -122.435932159424))
    list.add(MapLocation(61865, 37.850002964208095, -122.41632213957898))
    list.add(MapLocation(46713, 37.827799573006274, -122.42648773017541))
    list.add(MapLocation(19637, 37.82077, -122.4786))
    list.add(MapLocation(57109, 37.87572310328571, -122.50570595169079))
}

val reviewSummaries = mutableListOf<ReviewSummary>().also { list ->
    poiList.forEach {
        list.add(
            ReviewSummary(
                it.id,
                Random.nextDouble(1.0, 5.0),
                Random.nextInt(0, 20)
            )
        )
    }
}

val reviews = LongSparseArray<List<UserReview>>(poiList.size).also { map ->
    reviewSummaries.forEach {
        it.poiId?.let { id ->
            map.put(
                id,
                generateUserReviews(it)
            )
        }
    }
}

val mockBoundingBox = MapBoundingBox(north = 28.074301976209195, south = 27.837456158746768, east = -80.2721992135048, west = -80.65328747034074)
