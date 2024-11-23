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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Acteurs(navController: NavHostController, viewModel: MainViewModel,function: () -> Unit) {
    // État des acteurs observé depuis le ViewModel
    val actors by viewModel.actors.collectAsState()

    // Chargement initial des acteurs à l'ouverture de l'écran
    LaunchedEffect(Unit) {
        viewModel.getPopularActors()
    }

    Scaffold(
        topBar = { ActeursTopBar(viewModel) },
        bottomBar = { ActeursBottomBar(navController) }
    ) { paddingValues ->
        if (actors.isEmpty()) {
            // Affiche un texte si la liste est vide
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aucun acteur disponible")
            }
        } else {
            // Affichage en grille des acteurs
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 acteurs par ligne
                contentPadding = PaddingValues(
                    start = 8.dp,
                    end = 8.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding()
                ),
                modifier = Modifier.fillMaxSize()
            ) {
                items(actors) { actor ->
                    ActorGridItem(actor) {
                        // Naviguer vers l'écran des détails de l'acteur
                        navController.navigate("actor/${actor.id}")
                    }
                }
            }
        }
    }
}

// Composable pour un seul acteur dans la grille
@Composable
fun ActorGridItem(actor: TmdbPerson, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .border(1.dp, Color(0xFFEEB8D4), RoundedCornerShape(8.dp)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Affiche l'image de l'acteur
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data("https://image.tmdb.org/t/p/w500${actor.profile_path}")
                .crossfade(true)
                .build(),
            contentDescription = "Portrait de l'acteur",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2/3f)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        // Affiche le nom de l'acteur
        Text(
            text = actor.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
    }
}

// Composable pour la barre de navigation inférieure
@Composable
fun ActeursBottomBar(navController: NavHostController) {
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
fun ActeursTopBar(viewModel: MainViewModel) {
    var searchQuery by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Rechercher un acteur",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.width(16.dp))
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                viewModel.searchActors(searchQuery) // Recherche en temps réel des acteurs
            },
            label = { Text("Nom de l'acteur") },
            modifier = Modifier.weight(1f)
        )
    }
}
