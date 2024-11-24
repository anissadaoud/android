package com.example.premiereappli

import androidx.compose.foundation.ExperimentalFoundationApi
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
fun MovieDetailScreen(movieId: Int, navController: NavHostController, viewModel: MainViewModel) {
    // Collect the movie details state from the ViewModel
    val movieDetail by viewModel.movieDetail.collectAsState()

    // Fetch the movie details when the screen is shown
    LaunchedEffect(movieId) {
        viewModel.getMovieDetail(movieId) // Load movie details
    }

    // Check if the movieDetail is not null
    movieDetail?.let { movie ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Détails du film") },
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
                // Display the movie poster
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                        .crossfade(true)
                        .build(),
                    contentDescription = "Movie Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the movie title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the release date
                Text(
                    text = "Release: ${movie.release_date}",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Display the movie genres
                Text(
                    text = "Genre: ${movie.genres.joinToString(", ") { it.name }}",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Display the movie synopsis
                Text(
                    text = "Synopsis",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Check if cast is not null and not empty
                if (movie.cast?.isNotEmpty() == true) {
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
                        items(movie.cast) { actor ->
                            ActorItem(actor)
                        }
                    }
                } else {
                    // If no actors available, show a fallback message
                    Text("No actors available")
                }
            }
        }
    } ?: run {
        // Show a loading or error message if movieDetail is null
        Text(text = "Loading movie details...", modifier = Modifier.fillMaxSize(), textAlign = TextAlign.Center)
    }
}

@Composable
fun ActorItem(actor: Cast) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.width(120.dp)
    ) {
        // Display actor image with a fallback if profile_path is null or empty
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${actor.profile_path}")
                .crossfade(true)
                .build(),
            contentDescription = "Actor",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Gray)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Display actor's name with a fallback if it's null or empty
        Text(
            text = actor.name ?: "Unknown",  // Fallback if name is null
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1
        )

        // Display the character played by the actor with a fallback if it's null or empty
        Text(
            text = actor.character ?: "Unknown",  // Fallback if character is null
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}


