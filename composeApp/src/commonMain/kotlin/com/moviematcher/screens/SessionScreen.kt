package com.moviematcher.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SessionScreen(
    onBackToHome: () -> Unit,
    onShowResults: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Session Title
        Text(
            text = "Movie Session",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Placeholder for movie swiping
        Text(
            text = "Movie swiping interface will go here",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        // Show Results Button (temporary for navigation)
        Button(
            onClick = onShowResults,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Show Results",
                fontSize = 18.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Back Button
        Button(
            onClick = onBackToHome,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Back to Home",
                fontSize = 18.sp
            )
        }
    }
}
