package com.example.echohealth.Audiometry.AudiometryData
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "audiometry_results")
data class AudiometryResult(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ear: String,
    val frequency: Int,
    val decibels: Int
)
