package com.example.premiereappli

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Series(navController: NavHostController, viewModel: MainViewModel,function: () -> Unit) {
    // État des séries observé depuis le ViewModel
    val series by viewModel.series.collectAsState()

    // Chargement initial des séries à l'ouverture de l'écran
    LaunchedEffect(Unit) {
        viewModel.getPopularSeries()
    }

    Scaffold(
        topBar = { SeriesTopBar(viewModel) },
        bottomBar = { FilmsBottomBar(navController) }
    ) { paddingValues ->
        if (series.isEmpty()) {
            // Affiche un texte si la liste est vide
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aucune série disponible")
            }
        } else {
            // Affichage en grille des séries
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 Séries par ligne
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(series) { serie ->
                    SeriesGridItem(serie) {
                        // Naviguer vers l'écran des détails d'une série
                        navController.navigate("serie/${serie.id}")
                    }
                }
            }
        }
    }
}

// Composable pour un seul série dans la grille
@Composable
fun SeriesGridItem(serie: TmdbSeries, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFEEB8D4), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche l'image de la série
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${serie.poster_path}")
                .crossfade(true)
                .build(),
            contentDescription = "Affiche de la série",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f) // Format standard pour les affiches
                .clip(RoundedCornerShape(8.dp)) // Appliquer les coins arrondis à l'image
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Affiche le titre de la série
        Text(
            text = serie.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Affiche la date de première diffusion sous le titre
        Text(
            text = "Première diffusion: ${serie.first_air_date}",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}

// Composable pour la barre de recherche dans le haut de l'écran
@Composable
fun SeriesTopBar(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Rechercher une série",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchSeries(searchQuery) // Recherche en temps réel
            },
            label = { Text("Nom de la série") },
            modifier = Modifier.weight(1f)
        )
    }
}

// Composable pour la barre de navigation inférieure
@Composable
fun SeriesBottomBar(navController: NavHostController) {
    Text(
        text = "Barre de navigation ici",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        color = Color(0xFFEEB8D4) // Couleur de texte en rose
    )
}
