package com.example.echohealth.VirtualEar

import AudioRecorder
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log

fun AudioRecorder.startRecording() {
    Log.d("AudioRecorder", "Starting  recording")
    if (isRecording) return
    audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, 44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize)
    audioRecord?.startRecording()
    isRecording = true
    Thread {
        val audioBuffer = ShortArray(bufferSize)
        while (isRecording) {
            val readCount = audioRecord?.read(audioBuffer, 0, bufferSize) ?: 0
            if (readCount > 0) {
                synchronized(audioData) {
                    audioData.addAll(audioBuffer.take(readCount))

                }
            } else {
                Log.d("AudioRecorder", "No audio taken")
            }
        }
        Log.d("AudioRecorder", "Recording stopped")
    }.start()
}
fun AudioRecorder.stopRecording() {
    Log.d("AudioRecorder", "Stopping  recording")
    if (!isRecording) return

    isRecording = false
    audioRecord?.stop()
    audioRecord?.release()
    audioRecord = null
    Log.d("AudioRecorder", " recording stopped")
}