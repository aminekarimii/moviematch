package com.moviematcher.matching.presentation.matched_list

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.moviematcher.designsystem.component.button.LeadingIconButton
import com.moviematcher.designsystem.theme.DeepBlue20
import com.moviematcher.designsystem.theme.Grey80Transparent
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.designsystem.theme.dimens
import com.moviematcher.domain.models.Movie
import com.moviematcher.feature.matching.R
import org.koin.androidx.compose.koinViewModel


@Composable
fun MatchedResultListRoute(
    viewModel: MatchedResultViewModel = koinViewModel(),
    onNavigateToCreateSession: () -> Unit
) {
    HandleBackButton(onNavigateToCreateSession)
    MatchedResultListScreen(
        viewState = viewModel.viewState.collectAsStateWithLifecycle(MatchedResultState.Loading).value
    )
}
@Composable
fun HandleBackButton(onNavigateToCreateSession: () -> Unit){
    BackHandler {
        onNavigateToCreateSession()
    }
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
                    MatchedResultLoading()
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

            MatchedResultState.EmptyResult -> {
                MatchNotFoundContent()
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
fun MatchedResultLoading() {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_anim_icon)
    )

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.bigger),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            LottieAnimation(
                iterations = LottieConstants.IterateForever,
                composition = composition,
                modifier = Modifier
                    .fillMaxWidth(.4f)
                    .height(200.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "And the winners are...",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .aspectRatio(1f)
                .fillMaxWidth(),
            painter = painterResource(id = com.moviematcher.designsystem.R.drawable.img_waves),
            contentDescription = null
        )
    }
}

@Composable
internal fun MatchedResultListContent(
    moviesList: List<Movie>
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_icon)
    )
    Surface {
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
}

@Composable
internal fun MatchedMovieItem(movie: Movie) {
    val context = LocalContext.current
    Box {
        Box(
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        0.dp, 0.dp,
                        MaterialTheme.dimens.default,
                        MaterialTheme.dimens.default
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
                .padding(bottom = MaterialTheme.dimens.medium)
        ) {
            val (image, details) = createRefs()
            AsyncImage(
                contentDescription = null,
                model = movie.posterUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(110.dp)
                    .heightIn(min = 160.dp, max = 180.dp)
                    .padding(start = MaterialTheme.dimens.medium)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.large))
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
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
                    .padding(MaterialTheme.dimens.default)
            ) {
                Column(
                    modifier = Modifier
                        .padding(bottom = MaterialTheme.dimens.medium),

                    ) {
                    Text(
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.fillMaxWidth(),
                        text = movie.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                    Spacer(
                        modifier = Modifier.height(
                            MaterialTheme.dimens.medium
                        )
                    )
                    Text(
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp),
                        text = movie.overview,
                        maxLines = 2,
                        color = DeepBlue20
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    RatingBarContent(
                        rating = movie.voteAverage,
                    )

                    movie.trailerUrl?.let {
                        LeadingIconButton(
                            title = "Trailer"
                        ) {
                            context.startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    Uri.parse(movie.trailerUrl)
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBarContent(
    modifier: Modifier = Modifier,
    rating: Double
) {
    Row(
        modifier = modifier,
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
            style = MaterialTheme.typography.titleMedium,
            text = rating.toString(),
        )
    }
}

@Composable
fun MatchNotFoundContent() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(bottom = MaterialTheme.dimens.bigger),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                painter = painterResource(id = com.moviematcher.designsystem.R.drawable.ic_match_not_found),
                contentDescription = null,
                tint = Color.Unspecified,
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                modifier = Modifier.padding(bottom = 4.dp),
                text = "You haven’t matched any movie\nwith your teammate.",
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = "You can try another session ✌\uFE0F",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Image(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .aspectRatio(1f)
                .fillMaxWidth(),
            painter = painterResource(id = com.moviematcher.designsystem.R.drawable.img_waves),
            contentDescription = null
        )
    }
}


@Preview(showSystemUi = false, showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun PreviewMatchedResultListScreen() {
    MovieMatcherTheme {
        Surface {
            MatchedMovieItem(
                movie = Movie(
                    index = 1,
                    title = "Money Heist - La casa de papel 2017 from Netflix",
                    id = 2649,
                    year = "2018",
                    name = "Jimmy Wilson",
                    originalLanguage = "sapientem",
                    overview = "Money Heist - La casa de papel 2017 from NetflixMoney Heist - La casa de papel 2017 from NetflixMoney Heist - La casa de papel 2017 from Netflix",
                    posterUrl = null,
                    voteAverage = 2.3,
                    voteCount = 7278,
                    trailerUrl = "https://www.youtube.com/watch?v=Zb4O4wv2H2Y"
                )
            )
        }
    }
}