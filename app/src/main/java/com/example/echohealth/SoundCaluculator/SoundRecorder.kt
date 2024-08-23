package com.example.echohealth.SoundCaluculator
import Wavegen
import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.echohealth.IntroPage.CustomTopAppBar
import com.example.echohealth.Profile.fetchUserData
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.log10
import kotlin.math.sin
import kotlin.math.sqrt

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).apply {
        startActivity(this)
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecibelScreen(navController: NavController) {
    val context = LocalContext.current
    var decibels by remember { mutableStateOf(0.0) }
    var recording by remember { mutableStateOf(false) }
    val sizeOfBuffer = AudioRecord.getMinBufferSize(
        44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
    )
    var safeListeningTime by remember { mutableStateOf("calculating..") }
    var Permissiondeny by remember { mutableStateOf(0) }
    var allowDialog by remember { mutableStateOf(false) }
    var record by remember { mutableStateOf<AudioRecord?>(null) }
    val lightLevel by remember { derivedStateOf { decibels / 120 } }
    val permissionState = remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            )
        )
    }
    var totaldecibellevel by remember { mutableStateOf(0.0) }
    var totalduration by remember { mutableStateOf(0) }
    var meanDecibel by remember { mutableStateOf(0.0) }
    var decibelBox by remember { mutableStateOf(false) }
    var animbegin by remember { mutableStateOf(false) }
    LaunchedEffect(recording) {
        totalduration = 0
        totaldecibellevel = 0.0
        if (recording) {
            val data = ShortArray(sizeOfBuffer)
            while (recording) {
                record?.let {
                    val read = it.read(data, 0, sizeOfBuffer)
                    if (read >= 0) {
                        var sum = 0.0
                        var j = 0
                        while (j < read) {
                            sum += data[j] * data[j]
                            j++
                        }
                        if (read != 0) {
                            totaldecibellevel += decibels
                            totalduration += 1
                            val rms = sqrt(sum / read)
                            decibels = 20 * log10(rms)
                            decibels = maxOf(decibels, 0.0)

                            safeListeningTime = SafeListeningduration(decibels)
                        }
                    }
                    delay(1000L)
                }
            }
            if (totalduration >= 2) {
                meanDecibel = totaldecibellevel/ (totalduration - 1)
            } else {
                meanDecibel= 0.0
            }

            decibelBox = true
            animbegin = true
        }
    }
    fun startAudioRecording() {
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
            meanDecibel= 0.0
        }
        recording = false
        record = null
        decibelBox = true
    }
    val permissionLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGiven ->
            if (isGiven) {
                startAudioRecording()
            } else {
                Permissiondeny += 1
                if (Permissiondeny >= 2) {
                    allowDialog = true
                }
            }
        }
    )
    val userData = fetchUserData(LocalContext.current)
    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Sound Calculator",
                onBackButtonClick = { navController.navigate("main") },
                onProfileClick = {
                    if (userData != null) {
                        navController.navigate("profileDashboard")
                    } else {
                        navController.navigate("profilepage")
                    }
                }
            )
        }
    ) { paddingVal ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp, bottom = paddingVal.calculateBottomPadding())
        ) {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(paddingVal),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(15.dp))

                Box(
                    modifier = Modifier
                        .size(350.dp, 329.dp)
                        .padding(16.dp)
                        .clip(RoundedCornerShape(21.dp))
                        .background(color = Color(0xFF333333)),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(180.dp)
                            .align(Alignment.TopCenter)
                            .offset(y = 34.dp)
                            .border(0.6.dp, Color.White, shape = CircleShape)
                            .background(Color.Black, shape = CircleShape)
                    ) {
                        DecibellevelCircle(
                            level = lightLevel.toFloat(),
                            modifier = Modifier
                                .size(130.dp)
                                .align(Alignment.TopCenter)
                                .offset(y = 26.dp),
                            gradientColors = listOf(Color(0xFFD8FF5F), Color(0xFF29E33C)),
                            strokeWidth = 16.5.dp
                        )
                        Text(
                            text = "${decibels.toInt()}",
                            color = Color.White,
                            fontSize = 37.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 22.dp)
                        )
                        Text(
                            text = "Db",
                            color = Color.White,
                            fontSize = 12.sp,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(top = 40.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                    if (recording) {
                        Text(
                            text = "Recording your decibels",
                            color = Color.White,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(bottom = 35.dp)
                        )
                    }
                }
                Wavegen(isinRecording  = recording)
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        val presentPermission = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.RECORD_AUDIO
                        )
                        if (presentPermission == PackageManager.PERMISSION_GRANTED) {
                            if (recording) {
                                stopAudioRecording()
                            } else {
                                startAudioRecording()
                            }
                        } else {
                            permissionLaunch.launch(Manifest.permission.RECORD_AUDIO)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(46.dp),
                    modifier = Modifier
                        .width(230.dp)
                        .height(52.dp)
                ) {
                    Text(
                        text = if (recording) "STOP" else "START",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Button(
                    onClick = {
                        navController.navigate("main")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(46.dp),
                    modifier = Modifier
                        .width(230.dp)
                        .height(52.dp)
                ) {
                    Text(
                        text = "Back",
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
            if (decibelBox) {
                AvgDecibelBox(
                    isrecording = recording,
                    avgofDecibels = meanDecibel,
                    exit = {
                        decibelBox = false
                        if (animbegin) {
                            navController.navigate("videoScreen/${meanDecibel}")
                        }
                    }
                )
            }

            if (allowDialog) {
                val presentActivity = context as? Activity
                presentActivity?.let {
                    AlertDialog(
                        onDismissRequest = { allowDialog = false },
                        title = { Text("Permission needed") },
                        text = { Text("Allow permission to record audio. Please go to settings.") },
                        confirmButton = {
                            Button(onClick = {
                                allowDialog = false
                                it.openAppSettings()
                            }) {
                                Text("Settings")
                            }
                        },
                        dismissButton = {
                            Button(onClick = { allowDialog = false }) {
                                Text("Cancel")
                            }
                        }
                    )
                }
            }
        }
    }
}