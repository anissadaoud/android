package com.example.premiereappli
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.foundation.lazy.items

@Composable
fun Films(navController: NavHostController, viewModel : MainViewModel, onClick: () -> Unit) {
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchMovies(motcle="Hobbit")
    }

    LazyColumn{
        items(movies.size){ index ->
            Text(movies[index].original_title)
        }
    }

    Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onClick) {
                Text("Ecran précédent")
            }
        }

}

