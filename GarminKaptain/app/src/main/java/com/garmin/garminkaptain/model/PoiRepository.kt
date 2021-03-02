package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.data.PoiDTO
import com.garmin.garminkaptain.data.PoiDao
import com.garmin.garminkaptain.data.PointOfInterest
import kotlinx.coroutines.flow.Flow

class PoiRepository(private val poiDao: PoiDao) {

    fun getPoiList(): Flow<List<PointOfInterest>> = poiDao.getAllPoi()

    suspend fun getPoiDTOList(): List<PoiDTO> = poiDao.getAllPoiDTOs()

    fun getPoi(id: Long): Flow<PointOfInterest> = poiDao.getPoi(id)

    suspend fun getPoiDTO(id: Long) = poiDao.getPoiWithReviews(id)

    suspend fun getReviews(id: Long) = poiDao.getPoiWithReviews(id).list
}