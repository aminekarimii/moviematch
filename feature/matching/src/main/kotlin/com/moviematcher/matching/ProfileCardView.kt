package com.moviematcher.matching

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.moviematcher.designsystem.theme.MovieMatcherTheme

class Item(
    val id: String,
    val name: String,
    val age: Int,
    val picture: String
)

val dummy = Item(
    id = "1",
    name = "John Doe",
    age = 25,
    picture = "https://picsum.photos/200/300"
)
val dummy1 = Item(
    id = "2",
    name = "Hello from",
    age = 19,
    picture = "https://fastly.picsum.photos/id/923/200/300.jpg?hmac=eiYSYaG7v46VlrE38Amrg33bd2FzVjaCsQrLMdekyAU"
)

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDraggableCard() {
    MovieMatcherTheme {
        DraggableCardView(
            profile = dummy,
            modifier = Modifier.aspectRatio(.6f)
        )
    }
}

@Composable
fun DraggableCardView(
    modifier: Modifier = Modifier,
    profile: Item,
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
                modifier = Modifier.fillMaxWidth(),
                model = profile.picture,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}
