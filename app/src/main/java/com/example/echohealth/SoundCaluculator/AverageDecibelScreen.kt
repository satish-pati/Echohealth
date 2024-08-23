package com.example.echohealth.SoundCaluculator

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AvgDecibelBox(
    isrecording: Boolean,
    avgofDecibels: Double,
    exit: () -> Unit
) {
    if (!isrecording && avgofDecibels >= 0) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
                .clickable { exit }
            , contentAlignment = Alignment.TopCenter
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .align(Alignment.Center)
                    .fillMaxHeight(0.3f)
                    .fillMaxWidth(0.8f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF000000))
                    .padding(16.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "AVG DECIBELS : ${avgofDecibels.toInt()}",
                        style = TextStyle(fontStyle = FontStyle.Normal),
                        color = Color(0xFF29E33C),
                        fontSize = 23.sp,
                        textAlign = TextAlign.Center

                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = exit,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF29E33C),
                            contentColor = Color.Red
                        ),
                        shape = RoundedCornerShape(50),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        Text(text = "CLOSE")

                    }
                    Spacer(modifier = Modifier.height(8.dp))

                }
            }
        }
    }
}