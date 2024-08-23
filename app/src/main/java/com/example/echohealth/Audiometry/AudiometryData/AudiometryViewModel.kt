package com.example.echohealth.Audiometry.AudiometryData
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
class AudiometryViewModel(private val audiometryDao: AudiometryDao) : ViewModel() {
     private val _ltResults = MutableStateFlow<List<AudiometryResult>>(emptyList())
    val ltResults: StateFlow<List<AudiometryResult>> = _ltResults.asStateFlow()
    private val _rtResults = MutableStateFlow<List<AudiometryResult>>(emptyList())
    val rtResults: StateFlow<List<AudiometryResult>> = _rtResults.asStateFlow()
    private val _ltAvg = MutableStateFlow(0.0)
    val ltAvg: StateFlow<Double> = _ltAvg.asStateFlow()
    private val _rtAvg = MutableStateFlow(0.0)
     val rtAvg: StateFlow<Double> = _rtAvg.asStateFlow()
    private val _ltLevel = MutableStateFlow("")
   val ltLevel: StateFlow<String> = _ltLevel.asStateFlow()
    private   val _rtLevel = MutableStateFlow("")
   val rtLevel: StateFlow<String> = _rtLevel.asStateFlow()
    private val _OverallAvg = MutableStateFlow(0.0)
    val OverallAvg: StateFlow<Double> = _OverallAvg.asStateFlow()
    init {
        refreshData()
    }
     fun classifyHearingLevel(average: Double): String {
        return when {
            average <= 20 -> "Normal"
            average <= 40 -> "Mild"
            average <= 60 -> "Moderate"
            average <= 90 -> "Severe"
            else -> "Profound"
        }
    }

     fun refreshData() {
        viewModelScope.launch {
            try {

                _ltResults.value = audiometryDao. fetchLastsixLefttResults()
                _rtResults.value = audiometryDao. fetchLastsixRightResults()

                _rtAvg.value = if (_rtResults.value.isNotEmpty()) {
                    _rtResults.value.map { it.decibels }.average()
                } else {
                    0.0
                }

                _ltAvg.value = if (_ltResults.value.isNotEmpty()) {
                    _ltResults.value.map { it.decibels }.average()
                } else {
                    0.0
                }




                val TotalList =
                    (_ltResults.value + _rtResults.value).takeLast(12).map { it.decibels }
                _OverallAvg.value = if (TotalList.isNotEmpty()) {
                    TotalList.average()
                } else {
                    0.0
                }

            }
         catch (E: Exception) {
             Log.d("Audimetrymodel","error")
         }
         }
        }
    }

