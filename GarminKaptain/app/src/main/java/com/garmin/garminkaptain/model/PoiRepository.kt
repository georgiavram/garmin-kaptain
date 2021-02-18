package com.garmin.garminkaptain.model

import com.garmin.garminkaptain.data.poiList

object PoiRepository {

    fun getPoiList() = poiList

    fun getPoi(id: Long) = poiList.find { it.id == id }
}