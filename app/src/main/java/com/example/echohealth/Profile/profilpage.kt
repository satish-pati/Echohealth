package com.example.echohealth.Profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ProfileScreen(
                   onContinue: (String, String, String, Int) -> Unit,onBack: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var Intage by remember { mutableStateOf(0) }
    var isvalid by remember { mutableStateOf(true) }
    var errormsg by remember { mutableStateOf("") }

    val handleAgeChange: (String) -> Unit = { input ->
        age = input
        Intage = input.toIntOrNull() ?: 0
        isvalid = Intage > 0
    }
    val handleSubmit: () -> Unit = {

        when {
            name.isEmpty() -> errormsg = "Username cannot be empty"
            email.isEmpty() -> errormsg = "Email cannot be empty"
            gender.isEmpty() -> errormsg = "Gender cannot be empty"
            Intage <= 0 -> errormsg = "Please enter a valid age"
            else -> {
                errormsg = ""


                //val userData = UserData(username, email, gender, ageInt)
             //   saveUserData(userData, context)
               // onSave(username, gender, ageInt)
                onContinue(name, email, gender, Intage)
            }
        }
        isvalid = errormsg.isEmpty()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Help us finish setting up your account",
                modifier = Modifier.padding(bottom = 16.dp),
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Account Information",
                modifier = Modifier.padding(vertical = 8.dp),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            InputField(label = "Username", value = name, onValueChange = { name = it })
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Email", value = email, onValueChange = { email = it })
            Spacer(modifier = Modifier.height(8.dp))
            InputField(label = "Gender", value = gender, onValueChange = { gender = it })
            Spacer(modifier = Modifier.height(8.dp))
            InputField(
                label = "Age",
                value = age,
                onValueChange = handleAgeChange,
                keyboardType = KeyboardType.Number
            )

            if (!isvalid) {
                Text(
                    text = errormsg,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
   Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = handleSubmit,
                modifier = Modifier

                    .width(320.dp)
                    .height(55.dp)
                    .clip(RoundedCornerShape(24.dp)),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Green,
                    contentColor = Color.White,
                ),
                enabled = isvalid
            ) {
                Text(text = "Continue")
            }
        }


        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(horizontal = 16.dp)
                .align(Alignment.TopCenter)
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(top = 16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            Text(
                text = "Create an Account",
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(top = 40.dp),
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.W900,
                fontSize = 25.sp
            )
        }
    }
}
