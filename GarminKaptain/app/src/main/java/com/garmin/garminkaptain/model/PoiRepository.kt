package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.data.PoiDao
import com.garmin.garminkaptain.data.PointOfInterest
import kotlinx.coroutines.flow.Flow

class PoiRepository(private val poiDao: PoiDao) {

    fun getPoiList(): Flow<List<PointOfInterest>> = poiDao.getAllPoi()

    fun getPoi(id: Long): Flow<PointOfInterest> = poiDao.getPoi(id)
}