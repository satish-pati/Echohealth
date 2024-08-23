package com.example.echohealth.SoundCaluculator

import AudioRecorder
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AudioRecorderManager{
    var recording by mutableStateOf(false)
    var record: AudioRecord? = null
    var totaldecibellevel by mutableStateOf(0.0)
    var totalduration by mutableStateOf(0)
    var meanDecibel by mutableStateOf(0.0)
    var decibelBox by mutableStateOf(false)

    fun startAudioRecording(sizeOfBuffer: Int) {
        if (!recording) {
            record = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                44100,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                sizeOfBuffer
            )
            recording = true
            record?.apply {
                startRecording()
                recording = true
            }
        }
    }

    fun stopAudioRecording() {
        record?.apply {
            stop()
            release()
        }
        if (totalduration >= 2) {
            meanDecibel = totaldecibellevel / (totalduration - 1)
        } else {
            meanDecibel = 0.0
        }
        recording = false
        record = null
        decibelBox = true
    }
}
