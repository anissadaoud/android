package com.example.premiereappli

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun Films(navController: NavHostController, viewModel: MainViewModel,function: () -> Unit) {
    // État des films observé depuis le ViewModel
    val movies by viewModel.movies.collectAsState()

    // Chargement initial des films à l'ouverture de l'écran
    LaunchedEffect(Unit) {
        viewModel.getPopularMovies()
    }

    Scaffold(
        topBar = { FilmsTopBar(viewModel) },
        bottomBar = { FilmsBottomBar(navController) }
    ) { paddingValues ->
        if (movies.isEmpty()) {
            // Affiche un texte si la liste est vide
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aucun film disponible")
            }
        } else {
            // Affichage en grille des films
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 Films par lignes
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(movies) { movie ->
                    MovieGridItem(movie) {
                        // Naviguer vers l'écran des détails d'un film
                        navController.navigate("movieDetail/${movie.id}")
                    }
                }
            }
        }
    }
}

// Composable pour un seul film dans la grille
@Composable
fun MovieGridItem(movie: TmdbMovie, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFEEB8D4), RoundedCornerShape(8.dp))
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche l'image de l'affiche du film
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .crossfade(true)
                .build(),
            contentDescription = "Affiche du film",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f) // Format standard pour les affiches
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Affiche le titre
        Text(
            text = movie.original_title,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            maxLines = 2,
            textAlign = TextAlign.Center
        )
        // Affiche la date de sortie sous le titre
        Text(
            text = "Sortie: ${movie.release_date}",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

// Composable pour la barre de navigation inférieure
@Composable
fun FilmsBottomBar(navController: NavHostController) {
    Text(
        text = "Barre de navigation ici",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        color = Color(0xFFEEB8D4) // Couleur de texte en rose
    )
}

// Composable pour la barre de recherche dans le haut de l'écran
@Composable
fun FilmsTopBar(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Rechercher un film",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchMovies(searchQuery) // Recherche en temps réel
            },
            label = { Text("Titre du film") },
            modifier = Modifier.weight(1f)
        )
    }
}

