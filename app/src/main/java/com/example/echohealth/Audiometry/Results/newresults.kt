package com.example.echohealth.SoundCaluculator
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.echohealth.Audiometry.AudiometryData.AudiometryViewModel
import com.example.echohealth.Profile.fetchUserData
import java.text.SimpleDateFormat
import java.util.*
fun TodayDate(): String {
    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
    return dateFormat.format(Date())
}
@Composable
fun MyResultsScreen(navController: NavController,viewModel: AudiometryViewModel) {
    val leftEarAvg by viewModel.ltAvg.collectAsState()
    val leftEarval by viewModel.ltLevel.collectAsState()
    val rightEarAvg by viewModel.rtAvg.collectAsState()
    val rightEarval by viewModel.rtLevel.collectAsState()
    val avg by viewModel.OverallAvg.collectAsState()
    val userData = fetchUserData(LocalContext.current)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ){
        CustomTopAppBar(
            title = "Your Results",
            onBackButtonClick = { navController.navigate("main") },
            onProfileClick = {
                if (userData != null) {
                    navController.navigate("profileDashboard")
                } else {
                    navController.navigate("profilepage")
                }
            }
        )
        Box(
            modifier = Modifier
                .size(364.dp, 170.dp)
                .padding(14.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(Color(0xFF333333))
        ) {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = CurrentHearingLevel(avg),
                        color = Color(0XFF29E33C),
                        textAlign = TextAlign.Right,
                        modifier = Modifier.padding(horizontal = 10.dp),
                        fontSize = 22.sp
                    )
                    Text(
                        text = "Today",
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 70.dp)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = TodayDate(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = FirstLevel(avg),
                    color = Color.White,
                    maxLines = 2
                )
            }
        }

        Box(
            modifier = Modifier
                .size(364.dp, 233.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF333333)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = SecondLevel(avg) ,
                color = Color.White, modifier = Modifier.padding(horizontal = 12.dp),
                maxLines = 10
            )
        }

        Text(
            text = "Hearing Levels",
            color = Color.White,
            modifier = Modifier
                .padding(start = 20.dp, top = 1.dp),
            fontWeight = FontWeight.Bold
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(141.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HearingLevelBox(
                ear = "Left",
                decibels = String.format("%d dB", leftEarAvg.toInt()),
                level = leftEarval
            )
            HearingLevelBox(
                ear = "Right",
                decibels = String.format("%d dB", rightEarAvg.toInt()),
                level = rightEarval
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF333333)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Check", color = Color.White)
                TextButton(onClick = { navController.navigate("audiogramScreen") }) {
                    Text(text = "AUDIOGRAM", color = Color(0XFF29E33C))
                }
            }
        }
    }
}


@Composable
fun HearingLevelBox(ear: String, decibels: String, level: String) {
    Box(
        modifier = Modifier
            .size(174.dp, 141.dp)
            .padding(17.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(Color(0xFF333333))

    ){
        Column {
            Text(text = ear, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 12.dp))
            Spacer(modifier = Modifier.height(9.dp))
            Text(text = decibels, color =Color(0XFF29E33C),modifier = Modifier.padding(horizontal = 12.dp), fontSize = 23.sp, fontWeight = FontWeight.ExtraBold
            )

            Spacer(modifier = Modifier.height(9.dp))
            Text(text = level, color = Color.White,modifier = Modifier.padding(horizontal = 12.dp))
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    androidx.compose.material3.TopAppBar(colors = TopAppBarColors(
        containerColor = Color.Black,
        actionIconContentColor = Color.White,
        titleContentColor = Color.White,
        scrolledContainerColor = Color.Black,
        navigationIconContentColor = Color.White
    ),
        modifier = Modifier
            .background(Color.Black)
            .height(45.dp)
            .padding(top = 14.dp),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.material3.Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            androidx.compose.material3.IconButton(onClick = onBackButtonClick) {
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.background(Color.Black)
                )
            }
        },
        actions = {
            androidx.compose.material3.IconButton(
                onClick = onProfileClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(Color.Black, shape = CircleShape)
                    .border(3.dp, Color(0XFF29E33C), shape = CircleShape)
            )
                androidx.compose.material3.Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    )
}

fun CurrentHearingLevel(avgDecibels: Double): String {
    return when {
        avgDecibels <= 20 -> "Normal"
        avgDecibels <= 40 -> "Mild"
        avgDecibels <= 60 -> "Moderate"
        avgDecibels <= 90 -> "Severe"
        else -> "Profound"
    }
}

fun SecondLevel(avgDecibels: Double): String {
    return when {
        avgDecibels <= 30 -> "Your hearing test results indicate Healthy Ear.\n" +
                "Good Ear health keep it up , stay healthy avoid listening to loud noises.\n" +
                "Use eardrops "
        avgDecibels <= 60 -> "Your hearing test results indicate Mild Hearing Loss. At this level, you may have difficulty hearing soft sounds and understanding speech in noisy environments. While this type of hearing loss is not severe, it’s important to take steps to protect your hearing and prevent further decline. Consider using hearing aids if recommended by your audiologist, especially in situations where you struggle to hear clearly. Regular ear care, such as cleaning your ears gently and keeping them dry, can also help maintain your current hearing levels. Regular check-ups with a hearing specialist are advised to monitor your hearing and adjust your care plan as needed."

        avgDecibels <= 90 -> "Your hearing test results indicate Moderate Hearing Loss.\n"+" You may find it increasingly challenging to follow conversations without the use of hearing aids.\n" +"It’s crucial to take steps to protect your remaining hearing by avoiding loud environments and consulting with an audiologist for the best treatment options. Regular use of eardrops and ear care is also recommended.\n"
        avgDecibels <= 120 -> "Your hearing test results indicate Severe Hearing Loss. Communication in most situations may become very difficult without the use of hearing aids or other assistive listening devices. Immediate action is recommended, including consulting with a specialist to explore hearing aid options. Continue using eardrops and protect your ears from further damage by avoiding any exposure to loud noises."
        else ->"Your hearing test results indicate Profound Hearing Loss. This level of hearing loss means that even with the most powerful hearing aids, understanding speech without visual cues may be very challenging. It's important to consult with a specialist to explore advanced treatment options, such as cochlear implants or other assistive devices. Protect your ears from further damage, and continue to maintain ear health with regular use of eardrops and other recommended care practices"
    }
}

fun FirstLevel(avgDecibels: Double): String {
    return when {
        avgDecibels <= 30 -> "your hearing test result indicates normal hearing "
        avgDecibels<= 60 -> "your hearing test result indicates a mild hearing loss"
        avgDecibels <=90 -> "your hearing test result indicates a moderate hearing loss"
        avgDecibels <= 120 -> "your hearing test result indicates a severe hearing loss"
        else -> "Your hearing test result indicates a profound hearing loss."
    }
}