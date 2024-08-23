package com.example.echohealth.IntroPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    title: String,
    onBackButtonClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(colors = TopAppBarColors(containerColor = Color.Black, actionIconContentColor = Color.White, titleContentColor = Color.White, scrolledContainerColor = Color.Black, navigationIconContentColor = Color.White),
        modifier = Modifier.background(Color.Black).height(45.dp).padding(top = 14.dp),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.background(Color.Black  )
                )
            }
        },
        actions = {
            androidx.compose.material3.IconButton(
                onClick = onProfileClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(34.dp) // Adjust size as needed
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
            )   }


