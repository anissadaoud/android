package com.example.premiereappli

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController

@Composable
fun Acteurs(navController: NavHostController, viewModel : MainViewModel, onClick: () -> Unit) {
    val movies by viewModel.movies.collectAsState()

}


