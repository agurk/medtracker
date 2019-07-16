package com.timothymoll.medtracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MTRepository (private val mtDao: MTDAO) {

    val allEntries: LiveData<List<TakenMed>> = mtDao.getHistoryTaken()
    val currentEntries: LiveData<List<TakenMed>> = mtDao.getCurrentDetailsTaken()

    @WorkerThread
    suspend fun insert(medTaken: MedTaken) {
          mtDao.insert(medTaken)
    }

    @WorkerThread
    suspend fun addMedicine(newMed : MedDetails) {
        mtDao.addMed(newMed)
    }
}