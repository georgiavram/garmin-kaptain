package com.garmin.garminkaptain.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [PointOfInterest::class, UserReview::class], version = 2)
@TypeConverters(Converters::class)
abstract class PoiDatabase : RoomDatabase() {
    abstract fun getPoiDao(): PoiDao

    companion object {
        @Volatile
        private var INSTANCE: PoiDatabase? = null
        private const val DATABASE_NAME = "poi-database"

        private val roomListener = object : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                GlobalScope.launch {
                    INSTANCE?.getPoiDao()?.insertAllPoi(poiList)

                    poiList.forEach {
                        INSTANCE?.getPoiDao()?.insertAllReviews(reviews[it.id])
                    }
                }
            }
        }

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `user_review` (`id` INTEGER NOT NULL, `poiId` INTEGER NOT NULL, `rating` REAL NOT NULL," +
                            "`username` TEXT NOT NULL, `title` TEXT NOT NULL, `review` TEXT NOT NULL, " +
                            "`date` INTEGER NOT NULL, PRIMARY KEY(`id`))"
                )
            }
        }

        private fun buildDatabase(context: Context): PoiDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PoiDatabase::class.java,
                DATABASE_NAME
            )
                .addMigrations(MIGRATION_1_2)
                .build()
        }

        fun getInstance(context: Context): PoiDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }
    }
}