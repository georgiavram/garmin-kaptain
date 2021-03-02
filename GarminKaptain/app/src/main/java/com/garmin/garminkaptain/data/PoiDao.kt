package com.garmin.garminkaptain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Insert
    suspend fun insertAllReviews(reviews: List<UserReview>)

    @Insert
    suspend fun insertAllMapLocations(reviews: List<MapLocation>)

    @Insert
    suspend fun insertAllReviewSummaries(reviews: List<ReviewSummary>)

    @Delete
    suspend fun deletePoi(poi: PointOfInterest)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    fun getAllPoi(): Flow<List<PointOfInterest>>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): Flow<PointOfInterest>

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiWithReviews(id: Long): PoiDTO

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getAllPoiDTOs(): List<PoiDTO>

}