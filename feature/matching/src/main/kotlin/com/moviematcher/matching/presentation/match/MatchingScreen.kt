package com.moviematcher.matching.presentation.match

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.moviematcher.designsystem.theme.backgroundGradient
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.matching.presentation.matched_list.MovieModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchingRoute(
    onMatchingComplete: () -> Unit
) {
    MatchingScreen(
        onMatchingComplete = onMatchingComplete
    )
}

@Composable
fun MatchingScreen(
    viewModel: MatcherViewModel = koinViewModel(),
    onMatchingComplete: () -> Unit,
) {
    val viewState = viewModel.viewState.collectAsState().value
    Surface(
        modifier = Modifier
            .background(backgroundGradient)
            .fillMaxSize()
    ) {
        when(viewState) {
            is MatcherViewState.Loading -> {
                // Loading state
            }
            is MatcherViewState.Success -> {
                MatchingContent(
                    movies = viewState.matches,
                    onRemoveLastProfile = viewModel::removeLastProfile
                )
            }
            is MatcherViewState.Error -> {
                // Error state
            }
        }
    }
}

@Composable
fun MatchingContent(
    movies: List<MovieModel>,
    onRemoveLastProfile: () -> Unit
) {
    var counter by remember { mutableIntStateOf(0) }
    // var currentItem by remember { mutableStateOf(items[0]) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            AnimatedCounter(count = counter)

            Spacer(modifier = Modifier.height(21.dp))
            MatchHeader(onClick = {
                counter++
            })
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val swipeStates = movies.map { rememberSwipeableCardState() }
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                movies.forEachIndexed { index, movie ->
                    DraggableCardView(
                        movie = movie,
                        modifier = Modifier
                            .aspectRatio(.7f)
                            .swipableCard(
                                state = swipeStates[index],
                                onSwiped = {
                                    onRemoveLastProfile()
                                    /*items = items.dropLast(1)
                                    currentItem = profile*/
                                }
                            ),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .animateContentSize()
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column {
                    Text(
                        text = "currentItem.name",
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        text = "currentItem.age.toString()",
                        color = Color.White,
                    )
                }
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.Star,
                        tint = Color.White,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        text = "dummy.age.toString()",
                        color = Color.White,
                    )
                }
            }


        }
        Footer(
            modifier = Modifier
                .padding(bottom = MaterialTheme.dimens.large),
            counter = counter
        )
    }
}

@Composable
fun Footer(
    modifier: Modifier = Modifier,
    counter: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.White,
                    shape = MaterialTheme.shapes.medium
                )
                .padding(vertical = 24.dp, horizontal = 16.dp),
            text = counter.toString(),
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        val title =
            if (counter > 0) {
                "Your teammate has\nmatched some movies"
            } else "Swipe more to match\nwith your teammate"
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun MatchHeader(onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .height(46.dp)
            .background(Color(0xFF1B0A22), shape = MaterialTheme.shapes.large)
            .padding(horizontal = MaterialTheme.dimens.screenPaddingHorizontal),
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

@Preview(showSystemUi = false, showBackground = true)
@Composable
fun PreviewMatchingScreen() {
    MovieMatcherTheme {
        MatchingScreen {}
    }
}