package com.moviematcher.matching.presentation.match

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.domain.models.Movie

private val demoMovie = Movie(
    index = 1,
    title = "Money Heist - La casa de papel 2017 from Netflix",
    id = 2815,
    year = "2018",
    name = "Glen Robbins",
    originalLanguage = "quod",
    overview = "pretium",
    posterUrl = null,
    voteAverage = 6.7,
    voteCount = 6322,

)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDraggableCard() {
    MovieMatcherTheme {
        DraggableCardView(
            movie = demoMovie,
            modifier = Modifier.aspectRatio(.6f)
        )
    }
}

@Composable
fun DraggableCardView(
    modifier: Modifier = Modifier,
    movie: Movie,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Unspecified),
        modifier = Modifier
            .fillMaxWidth(.7f)
            .then(modifier),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(Modifier.fillMaxSize()) {
            AsyncImage(
                model = movie.posterUrl,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}
