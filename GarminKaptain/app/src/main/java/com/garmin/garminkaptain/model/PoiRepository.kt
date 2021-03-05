package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.data.PoiDTO
import com.garmin.garminkaptain.data.PoiDao
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.ReviewSummary
import com.garmin.garminkaptain.network.MockWebservice
import com.garmin.garminkaptain.network.Webservice
import kotlinx.coroutines.flow.Flow

class PoiRepository(
    private val poiDao: PoiDao,
    private val webservice: Webservice = MockWebservice()
) {
    private val dataIsStale = true

    suspend fun getPoiReviewSummary(id: Long): ReviewSummary {
        var result: ReviewSummary?
        val cacheSummary = poiDao.getPoiReviewSummary(id)
        result = cacheSummary

        if (cacheSummary == null || dataIsStale) {
            val response = webservice.getPoiReviewSummary(id).execute()
            if (response.isSuccessful) {
                response.body()?.reviewSummary.let {
                    result = it
                }
            }
        }
        return result ?: throw Exception("Empty Data!")
    }

    fun getPoiList(): Flow<List<PointOfInterest>> = poiDao.getAllPoi()

    suspend fun getPoiDTOList(): List<PoiDTO> = poiDao.getAllPoiDTOs()

    fun getPoi(id: Long): Flow<PointOfInterest> = poiDao.getPoi(id)

    suspend fun getPoiDTO(id: Long) = poiDao.getPoiWithReviews(id)

    suspend fun getReviews(id: Long) = poiDao.getPoiWithReviews(id).list

    suspend fun deletePoiById(id: Long) = poiDao.deletePoiById(id)
}