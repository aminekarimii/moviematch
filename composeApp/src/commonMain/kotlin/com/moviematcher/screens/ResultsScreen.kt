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
fun ResultsScreen(
    onBackToHome: () -> Unit,
    onNewSession: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Results Title
        Text(
            text = "Matched Movies",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )
        
        // Placeholder for matched movies
        Text(
            text = "Matched movies will be displayed here",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 48.dp)
        )
        
        // New Session Button
        Button(
            onClick = onNewSession,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = "Start New Session",
                fontSize = 18.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Back to Home Button
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
