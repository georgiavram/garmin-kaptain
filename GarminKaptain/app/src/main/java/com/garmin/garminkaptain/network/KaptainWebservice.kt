package com.garmin.garminkaptain.network

import com.garmin.garminkaptain.model.MapBoundingBox
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class KaptainWebservice : Webservice {

    override fun getPoiList(boundingBox: MapBoundingBox): Call<PoiListResponse> {
        return service.getPoiList(boundingBox)
    }

    override fun getPoiReviewSummary(id: Long): Call<PoiSummaryResponse> {
        return service.getPoiSummary(id)
    }

    companion object {
        private val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        private val interceptor = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }

        private val httpClient = OkHttpClient.Builder().apply {
            this.addInterceptor(interceptor)
        }

        private val service: KaptainApi = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(KaptainApi.BASE_URL)
            .client(httpClient.build())
            .build()
            .create(KaptainApi::class.java)
    }
}