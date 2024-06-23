package com.moviematcher.authentication.login.presentation.tutorial

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moviematcher.authentication.R
import com.moviematcher.designsystem.theme.DeepBlue20
import com.moviematcher.designsystem.theme.Grey60
import com.moviematcher.designsystem.theme.MovieMatcherTheme

data class TutorialStep(
    val index: String,
    val title: String,
    val description: String,
    val illustration: Int,
)

val TUTORIAL_STEPS = listOf(
    TutorialStep(
        index = "1.",
        title = "Invite your friend",
        description = "Share the generated code with your friend",
        illustration = R.drawable.share_session_pin_ill
    ),
    TutorialStep(
        index = "2.",
        title = "Swipe together",
        description = "Swipe right if you like a movie and swipe right if you don’t",
        illustration = R.drawable.tuto_swipe_cards_ill
    ),
    TutorialStep(
        index = "1.",
        title = "You got a match!",
        description = "You will get a list of movies or series that you have matched with your teammate.",
        illustration = R.drawable.tuto_confetti_ill
    )
)

@Composable
fun Steps(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .then(modifier),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        items(TUTORIAL_STEPS) { item ->
            TutorialRow(item)
        }
    }
}

@Composable
fun TutorialRow(item: TutorialStep) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            modifier = Modifier
                .height(155.dp)
                .weight(.4f),
            contentScale = ContentScale.Fit,
            painter = painterResource(id = item.illustration),
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(.6f),
        ) {
            Text(
                color = DeepBlue20,
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.headlineMedium,
                text = item.index
            )
            Text(
                fontWeight = FontWeight.ExtraBold,
                style = MaterialTheme.typography.titleMedium,
                text = item.title
            )
            Text(
                color = Grey60,
                style = MaterialTheme.typography.bodyMedium,
                text = item.description
            )
        }
    }
}

@Preview(
    showBackground = true, backgroundColor = 0xFF000000,
)
@Composable
private fun TutorialScreenPreview() {
    MovieMatcherTheme {
        Surface {
            val dummy = TutorialStep(
                index = "1.",
                title = "Invite your friend",
                description = "Share the generated code with your friend",
                illustration = R.drawable.share_session_pin_ill
            )
            TutorialRow(dummy)
        }
    }
}