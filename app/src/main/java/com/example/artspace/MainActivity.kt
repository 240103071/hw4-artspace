package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.artspace.ui.theme.ArtSpaceTheme
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.saveable.rememberSaveable


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFFF7F4EF)
                ) {
                    ArtSpaceScreen()
                }
            }
        }
    }
}

data class Artwork(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val authorRes: Int,
    @StringRes val yearRes: Int
)

fun artworkFor(id: Int): Artwork = when (id) {
    1 -> Artwork(
        R.drawable.dombra_modern,
        R.string.dombra_modern_title,
        R.string.dombra_modern_author,
        R.string.dombra_modern_year
    )
    2 -> Artwork(
        R.drawable.dombra_kurmangazy,
        R.string.dombra_kurmangazy_title,
        R.string.dombra_kurmangazy_author,
        R.string.dombra_kurmangazy_year
    )
    else -> Artwork(
        R.drawable.dombra_ancient,
        R.string.dombra_ancient_title,
        R.string.dombra_ancient_author,
        R.string.dombra_ancient_year
    )
}

@Composable
fun ArtSpaceScreen(modifier: Modifier = Modifier) {
    var currentArtwork by rememberSaveable { mutableStateOf(1) }
    val artwork = artworkFor(currentArtwork)

    val onPrevious = {
        currentArtwork = when (currentArtwork) {
            1 -> 3
            else -> currentArtwork - 1
        }
    }
    val onNext = {
        currentArtwork = when (currentArtwork) {
            3 -> 1
            else -> currentArtwork + 1
        }
    }

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp)
    ) {
        if (maxWidth > maxHeight) {
            Row(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 900.dp)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtworkFrame(
                    imageRes = artwork.imageRes,
                    description = stringResource(artwork.titleRes),
                    modifier = Modifier.weight(1f)
                )
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    ArtworkInfo(
                        title = stringResource(artwork.titleRes),
                        author = stringResource(artwork.authorRes),
                        year = stringResource(artwork.yearRes)
                    )
                    NavigationButtons(
                        onPrevious = onPrevious,
                        onNext = onNext
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .widthIn(max = 900.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ArtworkFrame(
                    imageRes = artwork.imageRes,
                    description = stringResource(artwork.titleRes),
                    modifier = Modifier.weight(1f)
                )
                ArtworkInfo(
                    title = stringResource(artwork.titleRes),
                    author = stringResource(artwork.authorRes),
                    year = stringResource(artwork.yearRes)
                )
                NavigationButtons(
                    onPrevious = onPrevious,
                    onNext = onNext
                )
            }
        }
    }
}

@Composable
fun ArtworkFrame(
    @DrawableRes imageRes: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.padding(vertical = 16.dp),
        color = Color.White,
        shadowElevation = 12.dp,
        border = BorderStroke(1.dp, Color(0xFFD8CFC0))
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = description,
            contentScale = ContentScale.Fit,
            modifier = Modifier.padding(24.dp)
        )
    }
}

@Composable
fun ArtworkInfo(
    title: String,
    author: String,
    year: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color(0xFFECE6DC))
            .padding(16.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Light
        )
        Text(
            text = author,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = year,
            color = Color.Gray
        )
    }
}

@Composable
fun NavigationButtons(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val woodColors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B5A2B))
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onPrevious,
            colors = woodColors,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.previous))
        }
        Button(
            onClick = onNext,
            colors = woodColors,
            modifier = Modifier.weight(1f)
        ) {
            Text(stringResource(R.string.next))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceScreen()
    }
}