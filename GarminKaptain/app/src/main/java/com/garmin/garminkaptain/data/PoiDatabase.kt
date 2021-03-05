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

@Database(entities = [PointOfInterest::class, UserReview::class, MapLocation::class, ReviewSummary::class], version = 5)
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
                    INSTANCE?.getPoiDao()?.apply {
                        println(reviewSummaries.toString())
                        insertAllPoi(poiList)
                        insertAllMapLocations(mapLocations)
                        insertAllReviewSummaries(reviewSummaries)
                    }

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

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE `poi_backup` (`id` INTEGER NOT NULL, `name` TEXT NOT NULL, `poiType` TEXT NOT NULL, PRIMARY KEY(`id`))"
                )
                database.execSQL(
                    "INSERT INTO `poi_backup` (`id`, `name`, `poiType`) SELECT id, name, poiType FROM poi_table"
                )
                database.execSQL(
                    "DROP TABLE poi_table"
                )
                database.execSQL(
                    "ALTER TABLE `poi_backup` RENAME TO `poi_table`"
                )
                database.execSQL(
                    "CREATE TABLE `map_location` (`locationId` INTEGER NOT NULL, `poiId` INTEGER NOT NULL, `latitude` REAL NOT NULL, `longitude` REAL NOT NULL," +
                            " PRIMARY KEY(`locationId`))"
                )
                database.execSQL(
                    "CREATE TABLE `review_summary` (`summaryId` INTEGER NOT NULL, `poiId` INTEGER NOT NULL, `averageRating` REAL NOT NULL," +
                            " `numberOfReviews` INTEGER NOT NULL, PRIMARY KEY(`summaryId`))"
                )
            }
        }


        private fun buildDatabase(context: Context): PoiDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PoiDatabase::class.java,
                DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
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