package com.moviematcher.matching.presentation.matched_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moviematcher.designsystem.theme.Grey80Transparent
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.feature.matching.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MatchedResultListRoute(
    viewModel: MatchedResultViewModel = koinViewModel()
) {
    MatchedResultListScreen(
        viewState = viewModel.viewState.collectAsStateWithLifecycle(MatchedResultState.Loading).value
    )
}

@Composable
fun MatchedResultListScreen(viewState: MatchedResultState) {
    Surface {
        when (viewState) {
            MatchedResultState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            MatchedResultState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "There is an error please try again later",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            is MatchedResultState.MatchedResults -> {
                MatchedResultListContent(
                    moviesList = viewState.movies
                )
            }
        }
    }
}

@Composable
internal fun MatchedResultListContent(
    moviesList: List<MovieModel>
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_icon)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.dimens.extraBig),
                style = MaterialTheme.typography.titleLarge,
                text = "Congratulations \uD83C\uDF89\uD83C\uDF89\uD83C\uDF89"
            )
            LazyColumn(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.dimens.screenPaddingHorizontal
                )
            ) {
                items(moviesList) {
                    MatchedMovieItem(movie = it)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }

        LottieAnimation(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .fillMaxHeight(.4f),
            composition = composition,
        )
    }
}

@Composable
internal fun MatchedMovieItem(movie: MovieModel) {
    Box {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        0.dp, 0.dp, 16.dp, 16.dp
                    ),
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Grey80Transparent
                        )
                    )
                )
                .align(Alignment.BottomCenter)
        )
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    bottom = 8.dp
                )
        ) {
            val (image, details) = createRefs()
            Image(
                modifier = Modifier
                    .height(150.dp)
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    },
                painter = painterResource(id = com.moviematcher.designsystem.R.drawable.img_movies_placeholder),
                contentDescription = null
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .constrainAs(details) {
                        top.linkTo(image.top)
                        start.linkTo(image.end)
                        end.linkTo(parent.end)
                        bottom.linkTo(image.bottom)
                        height = Dimension.fillToConstraints
                        width = Dimension.fillToConstraints
                    }
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        text = movie.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Text(
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                        text = movie.description
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = painterResource(id = com.moviematcher.designsystem.R.drawable.ic_star_colored),
                            contentDescription = null,
                            tint = Color.Unspecified
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            style = MaterialTheme.typography.bodyLarge,
                            text = "4.5",
                        )
                    }

                    Text(
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 28.sp
                        ),
                        color = Color.Red,
                        text = "#${movie.index}",
                    )
                }
            }
        }
    }
}

data class MovieModel(
    val index: Int,
    val title: String,
    val year: String,
    val length: String,
    val posterUrl: String
) {
    val description: String
        get() = "$year - $length"
}

@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewMatchedResultListScreen() {
    MovieMatcherTheme {
        Surface {
            MatchedMovieItem(
                movie = MovieModel(
                    index = 1,
                    title = "Money Heist - La casa de papel 2017 from Netflix",
                    year = "2021",
                    length = "2h 30m",
                    posterUrl = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
                )
            )
        }
    }
}