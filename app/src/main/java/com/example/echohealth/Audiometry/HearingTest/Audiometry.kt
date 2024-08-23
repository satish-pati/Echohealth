package com.example.echohealth.Audiometry.HearingTest

import android.content.Context
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.echohealth.Audiometry.AudiometryData.AudiometryDatabase
import com.example.echohealth.Audiometry.AudiometryData.AudiometryResult
import kotlin.math.pow
import kotlin.math.sin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class Audiometry(private var frequency: Int = 500, private val context: Context) {
    var currentDecibels by mutableStateOf(0)
    private var audioTrack: AudioTrack? = null
    private var isPlaying = false
    private val sampleRate = 44100
    private val numSamples = sampleRate
    private val wave = ByteArray(2 * numSamples)
    private val handler = Handler(Looper.getMainLooper())
    private var leftVolume = 0f
    private var rightVolume = 0f
    private val runnable = object : Runnable {
        override fun run() {
            if (isPlaying && currentDecibels < 120) {
                currentDecibels++
                val maxVolume = 1.0f
                val volume = (10.0.pow(currentDecibels / 20.0) / 10.0.pow(120 / 20.0)).toFloat()
                val scaledVolume = volume.coerceAtMost(maxVolume)
                audioTrack?.setStereoVolume(scaledVolume * leftVolume, scaledVolume * rightVolume)
                generateWave()
                audioTrack?.write(wave, 0, wave.size)

                handler.postDelayed(this, 100) // Update volume every 100ms
            }
        }
    }
    fun setFrequency(newFrequency: Int) {
        frequency = newFrequency
        generateWave()
    }
    fun startPlaying() {
        currentDecibels = 0// Reset decibels to 0 when starting to play
        generateWave()
        audioTrack = AudioTrack(
            AudioManager.STREAM_MUSIC,
            sampleRate,
            AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            wave.size,
            AudioTrack.MODE_STREAM
        )
        audioTrack?.play()
        isPlaying = true
        handler.post(runnable)
    }
    private fun generateWave() {
        val generatedSnd = DoubleArray(numSamples)
        for (i in 0 until numSamples) {
            generatedSnd[i] = sin(2 * Math.PI * i * frequency / sampleRate)
            val sampleValue = (generatedSnd[i] * Short.MAX_VALUE).toInt()
            wave[2 * i] = (sampleValue and 0x00ff).toByte()
            wave[2 * i + 1] = ((sampleValue and 0xff00) shr 8).toByte()
        }
    }
    fun stopPlaying(ear: String): Pair<Int, Int> {
        isPlaying = false
        audioTrack?.stop()
        audioTrack?.release()
        audioTrack = null
        handler.removeCallbacks(runnable)
        CoroutineScope(Dispatchers.IO).launch {
            val db = AudiometryDatabase.fetchDatabase(context)
            db.audiometryDao().insertResult(AudiometryResult(ear = ear, frequency = frequency, decibels = currentDecibels))
        }

        return Pair(frequency, currentDecibels)
    }
    fun selectLeftEar() {
        leftVolume = 1.0f
        rightVolume = 0.0f
    }
    fun selectRightEar() {
        leftVolume = 0.0f
        rightVolume = 1.0f
    }
}