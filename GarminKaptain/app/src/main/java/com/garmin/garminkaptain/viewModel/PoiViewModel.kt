package com.garmin.garminkaptain.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.garmin.garminkaptain.TAG
import com.garmin.garminkaptain.data.PoiDTO
import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PoiViewModel(application: Application) : AndroidViewModel(application) {
    private val poiRepository: PoiRepository by lazy {
        PoiRepository(PoiDatabase.getInstance(application).getPoiDao())
    }

    private val poiListLiveData: MutableLiveData<List<PoiDTO>> by lazy {
        MutableLiveData<List<PoiDTO>>()
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

    fun getPoi(id: Long): LiveData<PoiDTO?> = liveData {
        loadingDetailsLiveData.postValue(true)
        emit(poiRepository.getPoiDTO(id))
        loadingDetailsLiveData.postValue(false)
    }

    fun getPoiList(): LiveData<List<PoiDTO>> {
        loadPoiList()
        return poiListLiveData
    }

    fun loadPoiList() {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            poiListLiveData.postValue(poiRepository.getPoiDTOList())
            loadingLiveData.postValue(false)
        }
    }

    fun getLoading(): LiveData<Boolean> = loadingDetailsLiveData

    fun getLoadingList(): LiveData<Boolean> = loadingLiveData

    fun deletePoi(id: Long) {
        viewModelScope.launch {
            poiRepository.deletePoiById(id)
            loadPoiList()
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared() called")
    }

}