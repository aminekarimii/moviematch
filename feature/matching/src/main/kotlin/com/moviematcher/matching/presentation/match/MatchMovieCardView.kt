package com.moviematcher.matching.presentation.match

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.moviematcher.designsystem.theme.MovieMatcherTheme
import com.moviematcher.matching.presentation.matched_list.MovieModel

private val demoMovie = MovieModel(
    index = 1,
    title = "Money Heist - La casa de papel 2017 from Netflix",
    year = "2021",
    length = "2h 30m",
    posterUrl = "https://image.tmdb.org/t/p/w500/6MKr3KgOLmzOP6MSuZERO41Lpkt.jpg"
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
    movie: MovieModel,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.Unspecified),
        modifier = Modifier
            .fillMaxWidth(.7f)
            .then(modifier),
        shape = MaterialTheme.shapes.extraLarge
    ) {
        Column(Modifier.fillMaxSize()) {
            Image(
                modifier = Modifier.fillMaxWidth(),
                painter = painterResource(id = com.moviematcher.designsystem.R.drawable.img_movies_placeholder),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}
