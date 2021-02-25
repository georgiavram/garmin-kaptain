package com.garmin.garminkaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PointOfInterest
import com.garmin.garminkaptain.data.UserReview
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PoiViewModel(application: Application) : AndroidViewModel(application) {

    private val poiListLiveData: MutableLiveData<List<PointOfInterest>> by lazy {
        MutableLiveData<List<PointOfInterest>>()
    }

    private val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val loadingDetailsLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

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

    fun getPoi(id: Long): LiveData<PointOfInterest?> = liveData {
        loadingDetailsLiveData.postValue(true)
        PoiRepository.getPoi(getApplication(), id).collect {
            emit(it)
            loadingDetailsLiveData.postValue(false)
        }
    }

    fun getPoiList(): LiveData<List<PointOfInterest>> {
        loadPoiList()
        return poiListLiveData
    }

    fun getReviewsList(poiId: Long): LiveData<List<UserReview>> = liveData {
        loadingDetailsLiveData.postValue(true)
        PoiRepository.getPoi(getApplication(), poiId).collect { poi ->
            emit(poi.userReviews)
            loadingDetailsLiveData.postValue(false)
        }
    }

    fun loadPoiList() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            PoiRepository.getPoiList(getApplication()).collect {
                poiListLiveData.postValue(it)
                loadingLiveData.postValue(false)
            }
        }
    }

    fun getLoading(): LiveData<Boolean> = loadingDetailsLiveData

    fun getLoadingList(): LiveData<Boolean> = loadingLiveData

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}