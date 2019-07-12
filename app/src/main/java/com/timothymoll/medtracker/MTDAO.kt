package com.timothymoll.medtracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MTDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg medTaken: MedTaken)

    @Query("select * from taken_table where zDateTime < date('now') order by zDateTime desc")
    fun getHistoryTaken(): LiveData<List<MedTaken>>

    @Query("select * from taken_table where zDateTime >= date('now') order by zDateTime desc")
    fun getCurrentTaken(): LiveData<List<MedTaken>>
}