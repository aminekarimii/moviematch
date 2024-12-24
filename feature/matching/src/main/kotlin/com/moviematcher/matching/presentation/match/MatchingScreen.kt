package com.moviematcher.matching.presentation.match

import android.content.res.Configuration
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moviematcher.designsystem.component.divider.VerticalDivider
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.backgroundGradient
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.domain.models.Movie
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchingRoute(
    viewModel: MatcherViewModel = koinViewModel(),
    onMatchingComplete: () -> Unit
) {
    MatchingScreen(
        viewState = viewModel.viewState.collectAsState().value,
        onSwipe = viewModel::swipeLatestMovie,
        onMatchingComplete = onMatchingComplete
    )
}

@Composable
fun MatchingScreen(
    viewState: MatcherViewState,
    onSwipe: (Boolean) -> Unit,
    onMatchingComplete: () -> Unit,
) {
    Surface(
        modifier = Modifier
            .background(backgroundGradient)
            .fillMaxSize()
    ) {
        when (viewState) {
            is MatcherViewState.Loading -> {
                Text("Loading")
            }

            is MatcherViewState.Success -> {
                MatchingContent(
                    counter = viewState.counter,
                    movies = viewState.matches,
                    timeLeft = viewState.timer,
                    onSwipe = { swipingDirection ->
                        onSwipe(swipingDirection == SwipingDirection.Right)
                    }
                )
            }

            is MatcherViewState.MatchCompleted -> {
                LaunchedEffect(Unit) { onMatchingComplete() }
            }

            is MatcherViewState.Error -> {
                // Error state
            }
        }
    }
}


@Composable
fun MatchingContent(
    counter: Int = 0,
    timeLeft: Int,
    movies: List<Movie>,
    onSwipe: (SwipingDirection) -> Unit
) {
    var currentMovieIndex by remember { mutableIntStateOf(0) }



    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            AnimatedCounter(count = timeLeft)

            Spacer(modifier = Modifier.height(21.dp))
            MatchHeader()
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val swipeStates = movies.map { rememberSwipeableCardState() }
            Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                movies.forEachIndexed { index, movie ->
                    currentMovieIndex = index
                    DraggableCardView(
                        movie = movie,
                        modifier = Modifier
                            .aspectRatio(.7f)
                            .swipableCard(
                                state = swipeStates[index],
                                onSwiped = onSwipe
                            ),
                    )
                }
            }

            movies[currentMovieIndex].let { currentMovie ->
                Row(
                    modifier = Modifier
                        .animateContentSize()
                        .padding(
                            horizontal = MaterialTheme.dimens.extraBig,
                            vertical = 12.dp
                        ),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(modifier = Modifier.weight(.8f)) {
                        Text(
                            text = currentMovie.title,
                            color = Color.White,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Text(
                            text = "${currentMovie.year} - ",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                        )
                    }
                    Row(
                        modifier = Modifier.weight(.2f),
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = Icons.Default.Star,
                            tint = Color.Yellow,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            style = MaterialTheme.typography.titleMedium,
                            text = currentMovie.voteAverage.toString(),
                            color = Color.White,
                        )
                    }
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
private fun MatchHeader() {
    Row(
        modifier = Modifier
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

@Preview(
    showBackground = true, showSystemUi = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Composable
fun PreviewMatchingScreen() {
    MovieMatcherTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            MatchingContent(
                counter = 0,
                timeLeft = 10,
                movies = buildList {
                    repeat(5) {
                        add(
                            Movie(
                                index = 6407,
                                id = 7796,
                                year = "2017",
                                name = "Bert Patterson",
                                title = "It Ends with Us overview",
                                originalLanguage = "dolor",
                                overview = "blandit",
                                posterUrl = null,
                                voteAverage = 2.3,
                                voteCount = 4850
                            )
                        )
                    }
                },

                onSwipe = {}
            )
        }
    }
}