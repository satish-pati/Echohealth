package com.example.echohealth.Audiometry.AudiometryData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AudiometryDao {
    @Insert
    suspend fun insertResult(result: AudiometryResult)
    @Query("SELECT * FROM audiometry_results WHERE ear = 'right' ORDER BY id DESC LIMIT 6")
    suspend fun fetchLastsixRightResults(): List<AudiometryResult>

    @Query("SELECT * FROM audiometry_results WHERE ear = 'left' ORDER BY id DESC LIMIT 6")
    suspend fun  fetchLastsixLefttResults(): List<AudiometryResult>

    @Query("SELECT * FROM audiometry_results ORDER BY id DESC LIMIT 1")
    suspend fun fetchnewResult(): AudiometryResult?
    @Query("DELETE FROM audiometry_results")
    suspend fun deleteAllResults()
}
