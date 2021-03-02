package com.garmin.garminkaptain.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.data.UserReview
import com.garmin.garminkaptain.model.PoiRepository
import kotlinx.coroutines.launch

class ReviewViewModel(application: Application) : AndroidViewModel(application) {
    private val _reviewLiveData: MutableLiveData<List<UserReview>> by lazy {
        MutableLiveData<List<UserReview>>()
    }
    private val loadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    private val poiRepository: PoiRepository by lazy {
        PoiRepository(PoiDatabase.getInstance(application).getPoiDao())
    }

    val reviewLiveData: LiveData<List<UserReview>>
        get() = _reviewLiveData

    fun getReviews(id: Long) {
        loadingLiveData.postValue(true)
        viewModelScope.launch {
            _reviewLiveData.postValue(poiRepository.getReviews(id))
            loadingLiveData.postValue(false)
        }
    }

    fun getLoading(): LiveData<Boolean> = loadingLiveData
}