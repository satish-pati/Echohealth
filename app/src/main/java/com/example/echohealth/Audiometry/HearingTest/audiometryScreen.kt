package com.example.echohealth.Audiometry.HearingTest
import androidx.activity.compose.BackHandler
import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.example.echohealth.Audiometry.AudiometryData.AudiometryDatabase
import com.example.echohealth.Audiometry.AudiometryData.AudiometryResult
import com.example.echohealth.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AudiometryScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AudiometryDatabase.fetchDatabase(context)
    val audiometryDao = db.audiometryDao()
    var isPlaying by remember { mutableStateOf(false) }
    var frequencylist = listOf(250, 500, 1000, 2000, 4000, 8000)
    var presentFrequencyIndex by remember { mutableStateOf(0) }
    var frequency by remember { mutableStateOf(frequencylist[presentFrequencyIndex]) }
    var decibels by remember { mutableStateOf(0) }
    var dialogDisplay by remember { mutableStateOf(false) }
    var bt by remember { mutableStateOf(false) }
    var vm by remember { mutableStateOf(false) }
    var le by remember { mutableStateOf(false) }
    var re by remember { mutableStateOf(false) }

    var leftEarDecibels by remember { mutableStateOf<Int?>(null) }
    var rightEarDecibels by remember { mutableStateOf<Int?>(null) }

    val audiometry = remember { Audiometry(frequency, context) }
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    LaunchedEffect(Unit) {
        while (true) {
            bt = isBluetoothDeviceConnected(context)
            vm = isVolumeAtMax(audioManager)
            dialogDisplay= !bt || !vm
            delay(1000)
        }
    }
    BackHandler(enabled= isPlaying){
        if(isPlaying){
            audiometry.stopPlaying("both")
            isPlaying= false
            le=false
            re = false
        }
        navController.navigate("main")
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Hearing Test",
                onBackButtonClick = {
                    navController.navigate("main") },
                onProfileClick = { navController.navigate("profileDashboard") }
            )
        }
    ) {
        if (dialogDisplay) {
            Dialog(
                onDismissRequest = { },
                properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
            ) {
                Box(
                    modifier = Modifier
                        .width(400.dp)
                        .height(300.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF29E33C), // #29E33C
                                    Color(0xFF29E32A)  // #057511
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.headphone),
                                contentDescription = "Headphones",
                                modifier = Modifier.size(120.dp),
                                tint = Color.Unspecified
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Please establish a Bluetooth connection and maximize the volume to continue",
                                color = Color.Black,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Button(
                            onClick = {
                                navController.navigate("main")
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Red,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(36.dp),
                            modifier = Modifier
                                .width(150.dp)
                                .height(40.dp)
                        ) {
                            Text(text = "Back")
                        }
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.padding(bottom = 20.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.Top
                ) {
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (le) Color.Green else Color.Gray,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(36.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                    ) {
                        Text(text = "Left-Ear")
                    }
                    Spacer(modifier = Modifier.width(15.dp))
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (re) Color.Green else Color.Gray,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(46.dp),
                        modifier = Modifier
                            .width(120.dp)
                            .height(40.dp)
                    ) {
                        Text(text = "Right-Ear")
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Frequency:", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "$frequency Hz", color = Color.White, style = MaterialTheme.typography.bodyLarge)
                }

                DecibelLevelBar(decibels = audiometry.currentDecibels)


                Image(
                    painter = painterResource(id = R.drawable.audiometry),
                    contentDescription = "Audiometry Image",
                    modifier = Modifier
                        .size(275.dp)
                        .aspectRatio(1f)
                )
                val Scope = rememberCoroutineScope()
                Button(
                    onClick = {
                        if (!isPlaying) {
                            if (bt && vm) {
                                audiometry.selectLeftEar()
                                le = true
                                re = false
                                audiometry.setFrequency(frequency)
                                audiometry.startPlaying()
                                isPlaying = true
                            } else {
                                dialogDisplay= true
                            }
                        } else {
                            Scope.launch {
                                if (le) {
                                    val (stoppedFreq, stoppedDb) = audiometry.stopPlaying("left")
                                    frequency = stoppedFreq
                                    leftEarDecibels = stoppedDb
                                    audiometryDao.insertResult(AudiometryResult(ear = "left", frequency = frequency, decibels = stoppedDb))
                                    audiometry.selectRightEar()
                                    le = false
                                    re = true
                                    audiometry.setFrequency(frequency)
                                    audiometry.startPlaying()
                                } else if (re) {
                                    val (stoppedFreq, stoppedDb) = audiometry.stopPlaying("right")
                                    frequency = stoppedFreq
                                    rightEarDecibels = stoppedDb
                                    audiometryDao.insertResult(AudiometryResult(ear = "right", frequency = frequency, decibels = stoppedDb))
                                    if (presentFrequencyIndex < frequencylist.size - 1) {
                                        presentFrequencyIndex += 1
                                        frequency = frequencylist[presentFrequencyIndex]
                                        audiometry.setFrequency(frequency)
                                        audiometry.startPlaying()
                                        audiometry.selectLeftEar()
                                        le = true
                                        re = false
                                    } else {
                                        presentFrequencyIndex = 0
                                        isPlaying = false
                                        le = false
                                        re = false
                                        audiometry.stopPlaying("both")
                                        navController.navigate("audiogramScreen")
                                    }
                                }
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Green,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(46.dp),
                    modifier = Modifier
                        .width(250.dp)
                        .height(52.dp)
                ) {
                    Text(text = if (isPlaying && le) "Tap just after you hear" else if (isPlaying) "Tap just after you hear" else "START")
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}
fun isBluetoothDeviceConnected(context: Context): Boolean {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val devices = audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
    for (device in devices) {
        if (device.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP || device.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO) {
            return true
        }
    }
    return false
}

fun isVolumeAtMax(audioManager: AudioManager): Boolean {
    val presentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    return presentVolume == maxVolume
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(title: String, onBackButtonClick: () -> Unit, onProfileClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = "Profile", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFF29E33C))
    )
}
@Composable
fun DecibelLevelBar(decibels: Int) {
    val progress = decibels / 120f

    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Decibels: $decibels dB",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth(1f)
                .height(35.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color.Gray)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(Color.Green)
            )
        }
    }
}