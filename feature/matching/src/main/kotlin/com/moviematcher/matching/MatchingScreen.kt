package com.moviematcher.matching

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviematcher.designsystem.component.divider.VerticalDivider
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens

@Composable
fun MatchingScreenRoute() {

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewMatchingScreen() {
    MovieMatcherTheme {
        MatchingScreen()
    }

}

@Composable
fun MatchingScreen() {

    var counter by remember { mutableStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Box {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(60.dp))
                AnimatedCounter(count = counter)

                Spacer(modifier = Modifier.height(21.dp))
                MatchHeader(onClick = {
                    counter++
                })
            }
        }

    }
}

@Composable
private fun MatchHeader(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .height(46.dp)
            .background(Color(0xFF1B0A22), shape = MaterialTheme.shapes.large)
            .padding(horizontal = MaterialTheme.dimens.extraBig),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = Color.Red,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "right"
                //text = stringResource(R.string.matching_left_label),
            )
        }
        VerticalDivider(
            modifier = Modifier.padding(vertical = MaterialTheme.dimens.medium),
            thickness = 1.dp,
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "right"
                //text = stringResource(R.string.matching_right_label)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}