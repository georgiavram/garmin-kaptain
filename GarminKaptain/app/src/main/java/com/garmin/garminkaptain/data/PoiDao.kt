package com.garmin.garminkaptain.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PoiDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoi(poi: PointOfInterest)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPoi(poiList: List<PointOfInterest>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReviews(reviews: List<UserReview>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMapLocations(reviews: List<MapLocation>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReviewSummaries(reviews: List<ReviewSummary>)

    @Query("DELETE FROM poi_table WHERE id=:id")
    suspend fun deletePoiById(id: Long)

    @Update
    suspend fun updatePoi(poi: PointOfInterest)

    @Query("SELECT * from poi_table")
    fun getAllPoi(): Flow<List<PointOfInterest>>

    @Query("SELECT * from poi_table WHERE id=:id")
    fun getPoi(id: Long): Flow<PointOfInterest>

    @Query("SELECT * from review_summary WHERE poiId=:id")
    suspend fun getPoiReviewSummary(id: Long): ReviewSummary?

    @Transaction
    @Query("SELECT * FROM poi_table WHERE id=:id")
    suspend fun getPoiWithReviews(id: Long): PoiDTO

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getAllPoiDTOs(): List<PoiDTO>
}