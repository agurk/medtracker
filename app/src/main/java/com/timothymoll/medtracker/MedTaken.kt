package com.timothymoll.medtracker

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "taken_table")
data class MedTaken (
    var medicine: String,
    var zDateTime: String,
    var localDateTime: String
) { @PrimaryKey(autoGenerate = true)
    var id: Int = 0 }

