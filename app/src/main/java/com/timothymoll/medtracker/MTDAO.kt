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

    @Insert
    suspend fun addMed(vararg newMed: MedDetails)

    @Query("select * from med_details")
    fun getAllMeds(): List<MedDetails>

    @Query("select name, zDateTime, amount from taken_table, med_details where taken_table.medicineId = med_details.id and zDateTime < date('now') order by zDateTime desc")
    fun getHistoryTaken(): LiveData<List<TakenMed>>

  //  @Query("select * from taken_table, med_details where taken_table.medicineId = med_details.id and zDateTime >= date('now') order by zDateTime desc")
    @Query("select name, zDateTime, amount from taken_table, med_details where taken_table.medicineId = med_details.id and zDateTime >= date('now') order by zDateTime desc")
    fun getCurrentDetailsTaken(): LiveData<List<TakenMed>>

    @Query("select name, amount, medicineId, zDatetime, localDateTime from taken_table, med_details where taken_table.medicineId = med_details.id")
    fun exportAllValues(): List<ExportEntry>

}