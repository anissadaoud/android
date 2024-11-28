package com.example.premiereappli

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage

@Composable
fun PlaylistBottomBar(navController: NavHostController) {
    Text(
        text = "Barre de navigation ici",
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        textAlign = TextAlign.Center,
        color = Color(0xFFEEB8D4) // Couleur de texte en rose
    )

}

@Composable
fun Playlist (navController: NavHostController, viewModel: MainViewModel, function: () -> Unit) {
    val playlist by viewModel.playlist.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getPlaylist()
    }
    Scaffold(
        bottomBar = { PlaylistBottomBar(navController) }
    ) { paddingValues ->
        if (playlist.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aucune playlist disponible")
            }
        } else {

           // LazyVerticalGrid(
           //     columns = GridCells.Fixed(2),
            //    contentPadding = PaddingValues(
            //        start = 8.dp,
          //          end = 8.dp,
          //          top = paddingValues.calculateTopPadding(),
           //         bottom = paddingValues.calculateBottomPadding()
            //    )

             /*   AsyncImage(
                    model = "file:///android/images/2.jpg",
                    contentDescription ="",
                )

            ) */

        }
    }
}





