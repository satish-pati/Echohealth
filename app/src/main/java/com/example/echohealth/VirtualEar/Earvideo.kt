package com.example.echohealth.VirtualEar
import AudioRecorder
import android.Manifest
import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import com.example.echohealth.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@androidx.annotation.OptIn(UnstableApi::class)
@Composable
fun ExoPlayer(navController: NavController) {
    val avgdecibels = remember { mutableStateOf(0.0) }
    val coroutinescope = rememberCoroutineScope()
    val context = LocalContext.current
    val configuration = LocalConfiguration.current
    var dialog by remember { mutableStateOf(false) }
    val audiorecorder = remember { AudioRecorder(context) }
    var showNextButton by remember { mutableStateOf(false) }
    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGiven: Boolean ->
        if (isGiven) {
            coroutinescope.launch { audiorecorder.startRecording() }
        } else {
            Log.d("ExoPlayer", "Permission denied")
        }
    }
    var hasNextClicked by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }
    var dialogBackgroundColor by remember { mutableStateOf(Color.White) }
    LaunchedEffect(Unit) {
        requestPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
    }
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem1 = MediaItem.fromUri(
                Uri.parse("android.resource://${context.packageName}/${R.raw.video2}")
            )
            val mediaItem2 = MediaItem.fromUri(
                Uri.parse("android.resource://${context.packageName}/${R.raw.video3}")
            )
            setMediaItem(mediaItem1)
            prepare()
            playWhenReady = true

            addListener(object : Player.Listener {
                private var currentMediaIndex = 0

                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_ENDED) {
                        when (currentMediaIndex) {
                            0 -> {
                                audiorecorder.stopRecording()
                                avgdecibels.value = audiorecorder.calculateAverageDecibels()
                                val speed = when {
                                    avgdecibels.value > 100 -> 2.0f
                                    avgdecibels.value > 90 -> 1.9f
                                    avgdecibels.value > 80 -> 1.9f
                                    avgdecibels.value > 70 -> 1.75f
                                    avgdecibels.value > 60 -> 1.5f
                                    avgdecibels.value > 40 -> 1.0f
                                    else -> 0.5f
                                }
                                setMediaItem(mediaItem2)
                                prepare()
                                playbackParameters = PlaybackParameters(speed)
                                playWhenReady = true
                                repeatMode = Player.REPEAT_MODE_ONE
                                currentMediaIndex = 1
                            }
                            else -> {
                                Log.d("ExoPlayerView", "All videos ended")
                            }
                        }
                    }
                }

                override fun onPlayerError(error: PlaybackException) {
                    Log.e("ExoPlayerView", "Player error: ${error.message}")
                }
            })
        }
    }

    DisposableEffect(context) {
        onDispose {
            player.release()
            audiorecorder.stopRecording()
        }
    }
    LaunchedEffect(Unit){
        delay(10000)
        showNextButton = true
    }
    if (configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE) {
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    PlayerView(ctx).apply {
                        this.player = player
                        useController = false
                        setShowBuffering(PlayerView.SHOW_BUFFERING_NEVER)
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    }
                }
            )
            if (showNextButton) {
                Button(
                    onClick = {
                        if (!hasNextClicked) {
                            dialogMessage = when {
                                avgdecibels.value < 40 -> "Your surroundings are quiet.\nAs you see, your eardrum is functioning well with no future problems."
                                avgdecibels.value in 40.0..60.0 -> "Your surroundings are not quiet.\nAs you see, your eardrum is functioning normally with very minimal chance of hearing loss."
                                avgdecibels.value in 60.0..80.0 -> "Your surroundings are noisy.\nAs you see, your eardrum is beating fast, with a chance of partial tears and potential hearing loss."
                                else -> "Your surroundings are too noisy.\nAs you see, your eardrum is functioning abnormally, with a high chance of drum tear and hearing loss."
                            }
                            dialogBackgroundColor = when {
                                avgdecibels.value < 40 -> Color.Green
                                avgdecibels.value in 40.0..60.0 -> Color.Yellow
                                avgdecibels.value in 60.0..80.0 -> Color.Magenta
                                else -> Color.Red
                            }
                            dialog = true
                            hasNextClicked = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green),
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                        .width(120.dp)
                ) {
                    Text(text = "Next", color = Color.Black)
                }
            }
            Button(
                onClick = {
                    navController.navigate("main")
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Exit",
                    tint = Color.White
                )
            }
        }
    } else {

        LaunchedEffect(Unit) {
            dialog = true
        }

        if (dialog) {
            AlertDialog(
                onDismissRequest = { dialog = false },
                title = {
                    Text(
                        text = "Rotate Device",
                        color = Color.Black
                    )
                },
                text = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 100.dp)
                        ) {
                            Text(
                                text = "Please rotate your device to landscape mode to view the animation.",
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        val image: Painter = painterResource(id = R.drawable.rotate_phone)
                        Image(
                            painter = image,
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .width(100.dp)
                                .height(100.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            navController.navigate("main")
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit",
                            tint = Color.Black
                        )
                    }
                },
                backgroundColor = Color.Green,
                modifier = Modifier
                    .widthIn(min = 280.dp)
                    .heightIn(min = 200.dp)
            )
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Please rotate your device to landscape mode to view the animation.",
                    color = Color.Black
                )
            }
        }
    }
    if (dialog && hasNextClicked) {
        AlertDialog(
            onDismissRequest = {
                dialog = false
                navController.navigate("main")
            },
            title = {
                Text(
                    text = "Sound Environment Analysis",
                    color = Color.Black
                )
            },
            text = {
                Text(
                    text = dialogMessage,
                    color = Color.Black
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        dialog = false
                        navController.navigate("main")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Exit",
                        tint = Color.Black
                    )
                }
            },
            backgroundColor = dialogBackgroundColor,
            modifier = Modifier
                .widthIn(min = 280.dp)
                .heightIn(min = 200.dp)
        )
    }
}