package com.example.premiereappli
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
fun Films(navController: NavHostController, onClick: () -> Unit, viewModel : MainViewModel) {
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.searchMovies(motcle="Hobbit")
    }

    LazyColumn{
        items(movies){
            movie -> Text(text = movie.original_title)
        }
    }


    Scaffold(
        bottomBar = { Navbar(navController) }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Second écran")
            Button(onClick = onClick) {
                Text("Ecran précédent")
            }
        }
    }
}

