package com.garmin.garminkaptain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [PointOfInterest::class], version = 1)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao

    companion object {
        @Volatile
        private var INSTANCE: PoiDatabase? = null
        private const val DATABASE_NAME = "poi-database"

        private val roomListener = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                GlobalScope.launch {
                    INSTANCE?.getPoiDao()?.insertAllPoi(poiList)
                }
            }
        }

        private fun buildDatabase(context: Context): PoiDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PoiDatabase::class.java,
                DATABASE_NAME
            )
                .addCallback(roomListener)
                .build()
        }

        fun getInstance(context: Context): PoiDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
    }
}