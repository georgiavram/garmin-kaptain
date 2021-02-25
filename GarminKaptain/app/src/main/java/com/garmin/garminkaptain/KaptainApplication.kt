package com.garmin.garminkaptain

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.garmin.garminkaptain.data.PoiDatabase
import com.garmin.garminkaptain.data.poiList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class KaptainApplication : Application() {

    lateinit var poiDatabase: PoiDatabase

    private val roomListener = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            println(poiList.toString())

            GlobalScope.launch {
                poiDatabase.getPoiDao().insertAllPoi(poiList)
            }

        }
    }

    override fun onCreate() {
        super.onCreate()

        poiDatabase =
            Room.databaseBuilder(applicationContext, PoiDatabase::class.java, "poi-database")
                .addCallback(roomListener)
                .build()
    }
}