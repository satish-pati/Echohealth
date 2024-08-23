package com.example.echohealth
import EarHealthTipsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.echohealth.Audiometry.AudioGram.AudiogramScreen
import com.example.echohealth.Audiometry.AudiometryData.AudiometryDatabase
import com.example.echohealth.Audiometry.AudiometryData.AudiometryViewModel
import com.example.echohealth.Audiometry.AudiometryData.AudiometryViewModelFactory
import com.example.echohealth.Audiometry.HearingTest.AudiometryScreen
import com.example.echohealth.IntroPage.Intro
import com.example.echohealth.Profile.ProfileDashboard
import com.example.echohealth.Profile.ProfileScreen
import com.example.echohealth.Profile.UserData
import com.example.echohealth.Profile.fetchUserData
import com.example.echohealth.Profile.saveUserData
import com.example.echohealth.SoundCaluculator.DecibelScreen
import com.example.echohealth.SoundCaluculator.MyResultsScreen
import com.example.echohealth.VirtualEar.ExoPlayer
import com.example.echohealth.ui.theme.ECHOHEALTHTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ECHOHEALTHTheme {
                val userData = fetchUserData(this)
                var Dialog by remember { mutableStateOf(userData == null) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ){

                    val navController = rememberNavController()
                    NavigationGraph(navController = navController)

                }
            }
        }
    }
}
@Composable
fun MainScreen(navController: NavController = rememberNavController()) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Welcome Back!",
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    text = "",
                    color = Color.White,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            val userData= fetchUserData(LocalContext.current)
            Icon(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Profile",
                modifier = Modifier.background(Color.White)
                    .size(66.dp)
                    .clickable {
                        if (userData != null) {
                            navController.navigate("profileDashboard")
                        } else {
                            navController.navigate("profilepage")
                        }
                    }
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Echo Health",
            color = Color(0xFF29E33C),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Enhance your hearing now!",
            color = Color.White,
            fontSize = 16.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 30.dp),
            textAlign = TextAlign.Center
        )

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.DarkGray)
                .width(320.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "QUICK",
                    color = Color.White,
                    letterSpacing = 2.0.sp,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W900
                )
                Text(
                    text = "HEARING TEST",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W800
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .height(52.dp)
                        .width(290.dp)
                        .background(Color(0xFF29E33C))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    Button(
                        onClick = { navController.navigate("audiometryScreen") },
                        colors = ButtonDefaults.buttonColors(Color(0xFF29E33C)),
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .weight(1f)
                            .background(Color(0xFF29E33C)),
                        contentPadding = PaddingValues(0.dp)
                    ) {Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp), // Optional: add padding to the start for spacing
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start // Align text to the start
                    ) {
                        Text(
                            text = "HEARING TEST",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start, fontWeight = FontWeight.W800
                        )
                    }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrowfinal),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.DarkGray)
                .width(320.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "KNOW",
                    color = Color.White,
                    letterSpacing = 2.0.sp,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W900
                )
                Text(
                    text = "SURROUNDING NOISE",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W800
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .height(52.dp)
                        .width(320.dp)
                        .background(Color(0xFF29E33C))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    Button(
                        onClick = { navController.navigate("decibelScreen") },
                        colors = ButtonDefaults.buttonColors(Color(0xFF29E33C)),
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .weight(1f)
                            .background(Color(0xFF29E33C)),
                        contentPadding = PaddingValues(0.dp)
                    ) {Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "SOUND CALCULATION",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.W900
                        )
                    }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrowfinal),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(15.dp))


        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.DarkGray)
                .width(320.dp)

        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                Text(
                    text = "PEEK",
                    color = Color.White,
                    letterSpacing = 2.0.sp,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.W900
                )
                Text(
                    text = "INTO THE EAR",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W800
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .height(52.dp)
                        .width(320.dp)
                        .background(Color(0xFF29E33C))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    Button(
                        onClick = { navController.navigate("animationScreen/60.0") },
                        colors = ButtonDefaults.buttonColors(Color(0xFF29E33C)),
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxHeight()
                            .weight(1f)
                            .background(Color(0xFF29E33C)),
                        contentPadding = PaddingValues(0.dp)
                    ) {Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Text(
                            text = "VIRTUAL EAR",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                            fontWeight = FontWeight.W900
                        )
                    }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Image(
                        painter = painterResource(id = R.drawable.arrowfinal),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(top = 5.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        EarHealthTipsScreen()

    }

    }
@Composable
fun NavigationGraph(navController: NavHostController) {
    val context = LocalContext.current
    val userData = fetchUserData(context)
    NavHost(navController = navController, startDestination = "intro") {
        composable("intro") {
            Intro(
                StartedClick = { navController.navigate("main") },
                ProfileClick = {
                    if (userData != null) {
                        navController.navigate("profileDashboard")
                    } else {
                        navController.navigate("profilepage")
                    }
                }
            )
        }
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("decibelScreen") { DecibelScreen(navController = navController) }
        composable("profilepage") {
            ProfileScreen(
                onBack = { navController.popBackStack() },
                onContinue = { name, email, gender, age ->
                    val preferences = UserData(name, email, gender, age)
                    saveUserData(preferences, context)
                   // navController.popBackStack()
                    navController.navigate("profileDashboard")
                }
            )
        }
        composable("audiometryScreen") { AudiometryScreen(navController = navController) }
        composable("profileDashboard") {
            val cont= LocalContext.current
            val updatedUserData = fetchUserData(cont)
            ProfileDashboard(data = updatedUserData, Back = { navController.navigate("main")})
        }
        composable("animationScreen/{avgDecibel}") { backStackEntry ->
            val avgDecibel = backStackEntry.arguments?.getString("avgDecibel")?.toDouble()
            if (avgDecibel != null) {
                ExoPlayer(navController)
            }
        }
        composable("audiogramScreen") {
            val db = AudiometryDatabase.fetchDatabase(context)
            val audiometryDao = db.audiometryDao()

            val viewModel: AudiometryViewModel = viewModel(
                factory = AudiometryViewModelFactory(audiometryDao)
            )
            AudiogramScreen(viewModel = viewModel,navController)
        }
        composable("myResultsScreen") {
            val db = AudiometryDatabase.fetchDatabase(context)
            val audiometryDao = db.audiometryDao()

            val viewModel: AudiometryViewModel = viewModel(
                factory = AudiometryViewModelFactory(audiometryDao)
            )
            MyResultsScreen(viewModel = viewModel, navController = navController)
        }
    }
}


