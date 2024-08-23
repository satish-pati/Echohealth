package com.example.echohealth.IntroPage
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.echohealth.R

@Composable
fun Intro(StartedClick: () -> Unit, ProfileClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.intro),
            contentDescription = "Image",
            contentScale = ContentScale.Crop
            ,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(401.dp))
             Text(text = "Echo Health", fontWeight = FontWeight.Bold, textAlign = TextAlign.Center, fontSize =33.sp, color = Color.White)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Welcome to Echo Health -a user-friendly", textAlign =TextAlign.Start, fontSize = 13.5.sp,color=Color.White, modifier = Modifier.padding(top = 10.dp) )
            Text("gateway to your healthier lifestyle journey!", textAlign =TextAlign.Start, fontSize = 13.5.sp,color=Color.White,modifier=Modifier.padding(bottom=20.dp ))
            Spacer(modifier = Modifier.height(1
                .dp))

            Button(
                onClick = StartedClick,
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0XFF29E33C)
            ,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(27.dp),
                modifier = Modifier
                    .width(381.dp)
                    .height(60.dp)
            ) {
                Text(text = "Get Started", fontSize=21.sp)
            }
            Row( horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Don't have an account?", color = Color.White, fontSize = 13.sp ,modifier = Modifier.padding(start = 1.dp))
                Spacer(modifier = Modifier.width(5.5.dp))
                TextButton(onClick = ProfileClick) {
                    Text(text = "Sign up", color = Color(0XFF29E33C),fontSize = 13.sp  ,modifier = Modifier.padding(bottom = 1.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
