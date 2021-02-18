package com.garmin.garminkaptain.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.UserReview
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PoiViewModel : ViewModel() {

    init {
        Log.d(TAG, "init called")
        viewModelScope.launch(Dispatchers.IO + CoroutineName("Refresh-Poi-List")) {
            reloadPoiList()
        }
    }

    private suspend fun reloadPoiList() {
        while (true) {
            loadPoiList()
            delay(5000)
        }
    }

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val poiLiveData: MutableLiveData<PointOfInterest> by lazy {
        MutableLiveData<PointOfInterest>()
    }

    private val reviewsLiveData: MutableLiveData<List<UserReview>> by lazy {
        MutableLiveData<List<UserReview>>()
    }

    fun getPoi(id: Long): LiveData<PointOfInterest> {
        loadPoi(id)
        return poiLiveData
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> = poiListLiveData

    fun getReviewsList(): LiveData<List<UserReview>> {
        loadReviewsList()
        return reviewsLiveData
    }

    private fun loadPoiList() {
        poiListLiveData.postValue(PoiRepository.getPoiList())
    }

    private fun loadReviewsList() {
        reviewsLiveData.postValue(poiLiveData.value?.userReviews)
    }

    private fun loadPoi(id: Long) {
        poiLiveData.postValue(PoiRepository.getPoi(id))
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}