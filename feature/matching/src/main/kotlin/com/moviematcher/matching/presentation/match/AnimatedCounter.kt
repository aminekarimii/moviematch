package com.moviematcher.matching.presentation.match

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import java.util.Locale

@Composable
fun AnimatedCounter(
    count: Int,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodyLarge
) {
    var oldCount by remember { mutableIntStateOf(count) }
    SideEffect {
        oldCount = count
    }
    Row(modifier = modifier) {
        val minutes = count / 60
        val seconds = count % 60
        val oldMinutes = oldCount / 60
        val oldSeconds = oldCount % 60

        val countString = String.format(Locale.US, "%02d:%02d", minutes, seconds)
        val oldCountString = String.format(Locale.US, "%02d:%02d", oldMinutes, oldSeconds)

        for (i in countString.indices) {
            val oldChar = oldCountString.getOrNull(i)
            val newChar = countString[i]
            val char = if (oldChar == newChar) {
                oldCountString[i]
            } else {
                countString[i]
            }
            AnimatedContent(
                targetState = char,
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                },
                label = "AnimatedContent"
            ) { item ->
                Text(
                    text = item.toString(),
                    style = style,
                    softWrap = false
                )
            }
        }
    }
}