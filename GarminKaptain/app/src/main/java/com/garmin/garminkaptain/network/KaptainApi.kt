package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.data.UserReview
import com.garmin.garminkaptain.model.MapBoundingBox
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface KaptainApi {

    @POST("community/api/v1/points-of-interest/bbox")
    fun getPoiList(@Body boundingBox: MapBoundingBox): Call<PoiListResponse>

    //  @GET("community/api/v1/points-of-interest/{poiID}/summary?_={timestamp}")
    @GET("community/api/v1/points-of-interest/{poiID}/summary")
    fun getPoiSummary(
        @Path("poiID") poiId: Long,
        //@Query("timestamp") timestamp: Long
    ): Call<PoiSummaryResponse>


    @GET("community/api/v1/points-of-interest/{poiID}/reviews?_={timestamp}")
    fun getPoiReviews(
        @Path("poiID") poiId: Int,
        @Path("timestamp") timestamp: Long
    ): Call<List<UserReview>>

    companion object {
        const val BASE_URL = "https://activecaptain-stage.garmin.com/"
    }
}