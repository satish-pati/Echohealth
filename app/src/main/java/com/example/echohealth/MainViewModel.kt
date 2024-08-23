package com.example.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _isReady = MutableLiveData(false)
    val isReady: LiveData<Boolean> get() = _isReady

    init {

        simulateLoading()
    }

    private fun simulateLoading() {
        // Simulate some work with a delay
        // Here we just set it to true after 3 seconds
        Thread {
            Thread.sleep(1000)
            _isReady.postValue(true)
        }.start()
    }
}
