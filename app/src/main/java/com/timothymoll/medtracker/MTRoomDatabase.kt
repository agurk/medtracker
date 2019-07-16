package com.timothymoll.medtracker

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = [MedTaken::class, MedDetails::class], version = 1)
abstract class MTRoomDatabase : RoomDatabase() {
    abstract fun mtDAO(): MTDAO

    companion object {

        @Volatile
        private var INSTANCE: MTRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): MTRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MTRoomDatabase::class.java,
                    "mt_database"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback())
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback : RoomDatabase.Callback()


//            val tempInstance = INSTANCE
//            if (tempInstance != null) {
//                return tempInstance
//            }
//
//            synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MTRoomDatabase::class.java,
//                    "mt_database"
//                ).build()
//                    INSTANCE = instance
//                return instance
//            }

    }
}
