package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call

interface Webservice {
    fun getPoiList(boundingBox: MapBoundingBox): Call<PoiListResponse>
    fun getPoiReviewSummary(id: Long): Call<PoiSummaryResponse>
}