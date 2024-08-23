package com.example.echohealth.Audiometry.AudioGram
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.echohealth.Audiometry.AudiometryData.AudiometryViewModel

@Composable
fun AudiogramScreen(viewModel: AudiometryViewModel, navController: NavController) {
    val leftEarResults by viewModel.ltResults.collectAsState()
    val rightEarResults by viewModel.rtResults.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFF333333)),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Check", color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = {
                    navController.navigate("myResultsScreen")
                }) {
                    Text(text = "RESULTS", color = Color(0XFF29E33C))
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("Audiogram Results", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        if (leftEarResults.isNotEmpty() || rightEarResults.isNotEmpty()) {
            LineGraph(leftEarResults = leftEarResults, rightEarResults = rightEarResults)
        } else {
            Text("No recent results available")
        }
    }
}
