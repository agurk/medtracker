package com.timothymoll.medtracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MTViewModel(application: Application) : AndroidViewModel(application) {

    private val repository : MTRepository
    val allValues : LiveData<List<MedTaken>>

    init {
        val mtDAO = MTRoomDatabase.getDatabase(application, viewModelScope).mtDAO()
        repository = MTRepository(mtDAO)
        allValues = repository.allEntries
    }

    fun insert(medTaken: MedTaken) = viewModelScope.launch {
        repository.insert(medTaken)
    }
}