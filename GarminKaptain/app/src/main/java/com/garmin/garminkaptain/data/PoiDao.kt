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
    suspend fun getPoiWithReviews(id: Long): PoiWithReviews

    @Transaction
    @Query("SELECT * FROM poi_table")
    suspend fun getAllPoiWithReviews(): List<PoiWithReviews>

}