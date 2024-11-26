package com.example.premiereappli

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SerieDetailScreen(seriesId: Int, navController: NavHostController, viewModel: MainViewModel) {
    // Collect the series details
    val seriesDetail by viewModel.seriesDetail.collectAsState()
    val cast by viewModel.seriesCast.collectAsState()


    // Fetch the series details
    LaunchedEffect(seriesId) {
        viewModel.getSeriesDetail(seriesId) // series details
        viewModel.getSerieCast(seriesId) // the cast for the series
    }

    // Check if the seriesDetail is not null
    seriesDetail?.let { series ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Détails de la série") },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Retour")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                // Display the series poster
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500${series.poster_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Series Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the series title
                Text(
                    text = series.name,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the release date
                Text(
                    text = "Release: ${series.first_air_date}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the series genres
                Text(
                    text = "Genre: ${series.genres.joinToString(", ") { it.name }}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the series synopsis
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = series.overview,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the cast section
                Text(
                    text = "Actors",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Actor carousel
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(cast) { actor ->
                        Actor(actor = actor)
                    }
                }
            }
        }
    } ?: run {
        // Show a loading or error message if seriesDetail is null
        Text(text = "Loading series details...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}

@Composable
fun Actor(actor: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${actor.profile_path}")
                .crossfade(true)
                .build(),
            contentDescription = "Actor",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color(0xFFEEB8D4)) // Pink background
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        Text(
            text = actor.character,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}
