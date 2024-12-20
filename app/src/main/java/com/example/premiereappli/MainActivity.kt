package com.example.premiereappli


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.premiereappli.ui.theme.PremiereAppliTheme
import kotlinx.serialization.Serializable
import androidx.navigation.NavDestination.Companion.hasRoute

@Serializable class Profildestination
@Serializable class Filmsdestination
@Serializable class Seriesdestination
@Serializable class Acteursdestination
@Serializable class MovieDetaildestination
@Serializable class PlaylistDestination

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : MainViewModel by viewModels()
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

            PremiereAppliTheme {
                val navController = rememberNavController()
                Scaffold(bottomBar = { Navbar(navController) }) {
                    NavHost(navController = navController, startDestination = Profildestination()) {
                        composable<Profildestination> {
                            Profil(windowSizeClass) { navController.navigate(PlaylistDestination()) }
                        }
                        composable<PlaylistDestination> {
                            Playlist(navController, viewModel ) { navController.navigate(PlaylistDestination()) }
                        }
                        composable<Filmsdestination> {
                            Films(navController, viewModel ) { navController.navigate(Filmsdestination()) }
                        }
                        composable<Seriesdestination> {
                            Series(navController, viewModel ) { navController.navigate(Seriesdestination()) }
                        }
                        composable<Acteursdestination> {
                            Acteurs(navController, viewModel ) { navController.navigate(Acteursdestination()) }
                        }
                        composable("movieDetail/{movieId}") { backStackEntry ->
                            val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                            MovieDetailScreen(movieId = movieId, navController = navController, viewModel = viewModel)
                        }
                        composable("seriesDetail/{seriesId}") { backStackEntry ->
                            val seriesId = backStackEntry.arguments?.getString("seriesId")?.toInt() ?: 0
                            SerieDetailScreen(seriesId = seriesId, navController = navController, viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Navbar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    if (currentRoute?.hasRoute<Profildestination>() == false) {

        NavigationBar(
            containerColor = Color(0xFFEEB8D4) // Couleur de fond rose
        ) {
            NavigationBarItem(
                icon = { Icon(Icons.Default.Home, contentDescription = "Films") },
                label = { Text("Films") },
                selected = navController.currentDestination?.route == "films",
                onClick = { navController.navigate(Filmsdestination()) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.DateRange, contentDescription = "Series") },
                label = { Text("Series") },
                selected = navController.currentDestination?.route == "series",
                onClick = { navController.navigate(Seriesdestination()) }
            )
            NavigationBarItem(
                icon = { Icon(Icons.Default.Person, contentDescription = "Acteurs") },
                label = { Text("Acteurs") },
                selected = navController.currentDestination?.route == "acteurs",
                onClick = { navController.navigate(Acteursdestination()) }
            )

            NavigationBarItem(
                icon = { Icon(Icons.Default.PlayArrow, contentDescription = "Playlist") },
                label = { Text("Playlist") },
                selected = navController.currentDestination?.route == "playlist",
                onClick = { navController.navigate(PlaylistDestination()) }
            )
        }
    }
}

