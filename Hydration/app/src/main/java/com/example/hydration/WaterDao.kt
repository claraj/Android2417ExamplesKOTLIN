package com.example.hydration

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao  // data access object   -  important
interface WaterDao {

    // Other apps may omit the onConflict so an error is reported
    // if two records with the same primary key are inserted
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WaterRecord)

    @Update
    suspend fun update(record: WaterRecord)

    @Delete
    suspend fun delete(record: WaterRecord)

    @Query("SELECT * FROM WaterRecord WHERE day = :day LIMIT 1")
    fun getRecordForDay(day: String): Flow<WaterRecord>

    @Query("SELECT * FROM WaterRecord")
    fun getAllRecords(): Flow<List<WaterRecord>>
}