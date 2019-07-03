package com.timothymoll.medtracker

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class MTRepository (private val mtDao: MTDAO) {

        val allEntries: LiveData<List<MedTaken>> = mtDao.getAllEntries()

        @WorkerThread
        suspend fun insert(medTaken: MedTaken) {
            mtDao.insert(medTaken)
        }
    }