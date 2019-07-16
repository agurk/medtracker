package com.timothymoll.medtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taken_table")
data class MedTaken (
    var medicineId: Int,
    var zDateTime: String,
    var localDateTime: String
) { @PrimaryKey(autoGenerate = true)
    var id: Int = 0 }

@Entity(tableName = "med_details")
data class MedDetails (
    var name: String,
    var amount: Int
) { @PrimaryKey(autoGenerate = true)
    var id: Int = 0}

data class TakenMed(
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "zDateTime") val datetime: String,
    @ColumnInfo(name = "amount") val amount : Int
)

data class ExportEntry(
    @ColumnInfo(name = "name") val name : String,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name= "medicineId") val medicineId: Int,
    @ColumnInfo(name= "zDateTime") val zDateTime: String,
    @ColumnInfo(name = "localDateTime") val localDateTime: String
)



