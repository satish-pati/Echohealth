package com.example.echohealth.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.echohealth.data.entity.ExposureData

@Dao
interface ExposureDataDao {
    @Insert
    suspend fun insertData(data:ExposureData)
    @Query("SELECT * FROM exposuredetails")
    suspend fun getExposureData():List<ExposureData>
    @Query("SELECT * FROM exposuredetails WHERE id = :id")
    suspend fun ExposureDatabyId(id: Int): ExposureData?
    @Query("SELECT COUNT(*) FROM exposuredetails WHERE id = :id")
    suspend fun countRowsById(id: Int): Int
}
