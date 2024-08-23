import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import kotlin.math.log10
import kotlin.math.sqrt

class AudioRecorder( val context: Context) {
  var isRecording = false
   var audioRecord: AudioRecord? = null
    val bufferSize = AudioRecord.getMinBufferSize(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
    val audioData = mutableListOf<Short>()
    fun calculateAverageDecibels(): Double {
        Log.d("AudioRecorder", "Calculating avg decibels")
        synchronized(audioData) {
            if (audioData.isEmpty()) {
                Log.d("AudioRecorder", "No audio data ")
                return 0.0
            }
            val bufferSize = audioData.size
            val audioBuffer = audioData.toShortArray()
            var rmsSum = 0.0
            for (sample in audioBuffer) {
                rmsSum += (sample * sample).toDouble()
            }
            val rms = sqrt(rmsSum / bufferSize)
            val decibels = 20 * log10(rms.coerceAtLeast(1e-6))
            Log.d("AudioRecorder", "Calculated RMS: $rms, Decibels: $decibels")
            audioData.clear()
            return decibels
        }
    }
}
