package com.moviematcher.matching.presentation.matched_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.feature.matching.R

@Composable
fun NoMatchFoundRoute() {
    NoMatchFoundScreen()
}

@Composable
fun NoMatchFoundScreen() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.waves)
    )
    Surface {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Try Again",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            // align in the center of the parent column
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                /*Icon(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.match_not_found),
                    contentDescription = "No Match Found",
                )*/

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "You haven’t matched any movie\nwith your teammate.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "You can try another session",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                LottieAnimation(
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier.fillMaxWidth(),
                    composition = composition,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = false)
@Composable
fun PreviewNoMatchFoundScreen() {
    MovieMatcherTheme {
        NoMatchFoundScreen()
    }
}