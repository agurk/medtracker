package com.timothymoll.medtracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MTRepository (private val mtDao: MTDAO) {

    val allEntries: LiveData<List<MedTaken>> = mtDao.getHistoryTaken()
    val currentEntries: LiveData<List<MedTaken>> = mtDao.getCurrentTaken()

    @WorkerThread
    suspend fun insert(medTaken: MedTaken) {
          mtDao.insert(medTaken)
    }
}