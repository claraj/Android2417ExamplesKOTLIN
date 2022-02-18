package com.example.hydration

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WaterViewModel(private val repository: WaterRepository): ViewModel() {

    // We'll need this frequently so keep a copy here
    val allRecords = repository.getAllRecords().asLiveData()

    fun insertNewRecord(record: WaterRecord) {
        viewModelScope.launch() {
            repository.insert(record)
        }
    }

    fun updateRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.update(record)
        }
    }

    fun deleteRecord(record: WaterRecord) {
        viewModelScope.launch {
            repository.delete(record)
        }
    }

    fun getRecordForDay(day: String): LiveData<WaterRecord> {
        return repository.getRecordForDay(day).asLiveData()
    }
}

class WaterViewModelFactory(private val repository: WaterRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(repository) as T
        }
        throw IllegalArgumentException("$modelClass class is not a WaterViewModel")
    }

}

