package com.timothymoll.medtracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MTViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MTRepository
    val historyValues : LiveData<List<MedTaken>>
    val currentValues : LiveData<List<MedTaken>>

    init {
        val mtDAO = MTRoomDatabase.getDatabase(application, viewModelScope).mtDAO()
        repository = MTRepository(mtDAO)
        historyValues = repository.allEntries
        currentValues = repository.currentEntries
    }

    fun insert(medTaken: MedTaken) = viewModelScope.launch {
        repository.insert(medTaken)
    }
}