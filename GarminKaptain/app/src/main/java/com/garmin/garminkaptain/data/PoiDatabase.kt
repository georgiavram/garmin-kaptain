package com.garmin.garminkaptain.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PointOfInterest::class], version = 1)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao
}