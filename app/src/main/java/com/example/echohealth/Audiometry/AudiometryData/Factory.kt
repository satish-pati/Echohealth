package com.example.echohealth.Audiometry.AudiometryData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class AudiometryViewModelFactory(
    private val audioDao: AudiometryDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AudiometryViewModel::class.java)) {
            return AudiometryViewModel(audioDao) as T
        }
        throw IllegalArgumentException("exception occured in audiometrydatabase")
    }
}
