package com.timothymoll.medtracker

import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [MedTaken::class], version = 3)
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
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class WordDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onOpen method to populate the database.
             * For this sample, we clear the database every time it is created or opened.
             */
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                INSTANCE?.let { database ->
//                    scope.launch {
//                        populateDatabase(database.mtDAO())
//                    }
//                }
            }
        }


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
