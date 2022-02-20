package com.example.hydration

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class WaterViewModel(private val waterRepository: WaterRepository, private val daysRepository: DaysRepository): ViewModel() {

    // We'll need this frequently so keep a copy here
    val allRecords = waterRepository.getAllRecords().asLiveData()

    fun insertNewRecord(record: WaterRecord) {
        viewModelScope.launch() {
            waterRepository.insert(record)
        }
    }

    fun updateRecord(record: WaterRecord) {
        viewModelScope.launch {
            waterRepository.update(record)
        }
    }

    fun deleteRecord(record: WaterRecord) {
        viewModelScope.launch {
            waterRepository.delete(record)
        }
    }

    fun getRecordForDay(day: String): LiveData<WaterRecord> {
        return waterRepository.getRecordForDay(day).asLiveData()
    }


    fun getWeekdays(): List<String> {
        return daysRepository.weekdays
    }


    fun getTodayIndex(): Int {
        return daysRepository.todayIndex
    }

}

class WaterViewModelFactory(private val waterRepository: WaterRepository, private val daysRepository: DaysRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WaterViewModel::class.java)) {
            return WaterViewModel(waterRepository, daysRepository) as T
        }
        throw IllegalArgumentException("$modelClass class is not a WaterViewModel")
    }

}

