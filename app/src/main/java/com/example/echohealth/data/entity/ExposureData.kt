package com.example.echohealth.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="exposuredetails")
data class ExposureData (
    @PrimaryKey(autoGenerate = true) val id:Int =0,
    val decibelMin:Double,
    val decibelMax:Double,
    val exposureTime:Double,
    val frequency:String,
    val effect:String,
    val FutureHearingProblems:String,
    val recommendations:String
)
