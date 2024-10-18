package com.example.codingchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.codingchallenge.data.model.CARD_TYPE
import com.example.codingchallenge.data.model.CardItem
import com.example.codingchallenge.ui.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt

// fallback / default values
private const val DEFAULT_FONT_COLOR = "#000000"
private const val DEFAULT_FONT_SIZE = 15
private const val DEFAULT_ROW_PADDING = 10

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeScreen {
                HomeList(
                    homeViewModel
                )
            }
        }
    }
}


@Composable
fun HomeScreen(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}


@Composable
fun HomeList(homeViewModel: HomeViewModel) {
    val cards = homeViewModel.cards.collectAsState()

    LaunchedEffect(Unit) {
        homeViewModel.fetchData()
    }

    if (cards.value.isEmpty()) {
        // Placeholder, message, or loading screen if there are no items
        Text("Loading Items")
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(cards.value) { card ->
                Column {
                    when (card.type) {
                        CARD_TYPE.TEXT -> TextCard(card)
                        CARD_TYPE.TITLE -> TitleCard(card)
                        CARD_TYPE.IMAGE_TITLE -> ImageCard(card)
                    }
                    Divider() // Added this divider after each item for better visibility
                }
            }
        }
    }
}


@Composable
fun TextCard(card: CardItem) {
    val style = TextStyle(
        color = Color(
            card.fontColor?.toColorInt() ?: DEFAULT_FONT_COLOR.toColorInt()
        ),
        // assumption - font size is returned from API as sp. otherwise, additional conversion needed
        fontSize = card.fontSize?.sp ?: DEFAULT_FONT_SIZE.sp
    )
    // i created a default row padding value to give the items some room
    Row(modifier = Modifier.padding(DEFAULT_ROW_PADDING.dp)) {
        Text(text = card.value ?: "", color = style.color, fontSize = style.fontSize)
    }
}


@Composable
fun TitleCard(card: CardItem) {
    Column(modifier = Modifier.padding(DEFAULT_ROW_PADDING.dp)) {

        val titleStyle = TextStyle(
            color = Color(
                card.title?.attributes?.color?.toColorInt() ?: DEFAULT_FONT_COLOR.toColorInt()
            ),
            fontSize = card.title?.attributes?.font?.size?.sp ?: DEFAULT_FONT_SIZE.sp
        )

        val descriptionStyle = TextStyle(
            color = Color(
                card.description?.attributes?.color?.toColorInt() ?: DEFAULT_FONT_COLOR.toColorInt()
            ),
            fontSize = card.description?.attributes?.font?.size?.sp ?: DEFAULT_FONT_SIZE.sp
        )

        Text(
            text = card.title?.value ?: "",
            color = titleStyle.color,
            fontSize = titleStyle.fontSize
        )
        Text(
            text = card.description?.value ?: "",
            color = descriptionStyle.color,
            fontSize = descriptionStyle.fontSize
        )
    }
}


@Composable
fun ImageCard(card: CardItem) {
    var imageUrl = card.image?.url

    Box {
        val painter = rememberAsyncImagePainter(model = imageUrl)
        val width = card.image?.size?.width?.toFloat() ?: 1f
        val height = card.image?.size?.height?.toFloat() ?: 1f

        val titleStyle = TextStyle(
            color = Color(
                card.title?.attributes?.color?.toColorInt() ?: DEFAULT_FONT_COLOR.toColorInt()
            ),
            // assumption - font size is returned from API as sp. otherwise, addition conversion needed
            fontSize = card.title?.attributes?.font?.size?.sp ?: DEFAULT_FONT_SIZE.sp
        )

        val descriptionStyle = TextStyle(
            color = Color(
                card.description?.attributes?.color?.toColorInt() ?: DEFAULT_FONT_COLOR.toColorInt()
            ),
            fontSize = card.description?.attributes?.font?.size?.sp ?: DEFAULT_FONT_SIZE.sp
        )

        // prevent extreme case of dividing by 0
        val ratio = if (height > 0) width / height else 1f

        Image(
            painter = painter,
            contentDescription = "image for ${card.title?.value}", // TODO - api could return contentDescription
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(ratio)
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(15.dp) // padding not specified in api response, but i added here to match example graphic
        ) {
            Text(
                text = card.title?.value ?: "",
                color = titleStyle.color,
                fontSize = titleStyle.fontSize
            )
            Text(
                text = card.description?.value ?: "",
                color = descriptionStyle.color,
                fontSize = descriptionStyle.fontSize
            )
        }
    }
}