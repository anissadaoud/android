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
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.premiereappli.ui.theme.PremiereAppliTheme
import kotlinx.serialization.Serializable

@Serializable class Profildestination
@Serializable class Filmsdestination

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
                Scaffold {
                    NavHost(navController = navController, startDestination = Profildestination()) {
                        composable<Profildestination> {
                            Profil(windowSizeClass) { navController.navigate(Filmsdestination()) }
                        }
                        composable<Filmsdestination> {
                            Films(navController, viewModel ) { navController.navigate(Profildestination()) }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun Navbar(navController: NavHostController) {
    NavigationBar {

        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Films") },
            label = { Text("Films") },
            selected = navController.currentDestination?.route == "films",
            onClick = { navController.navigate("films") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.DateRange, contentDescription = "Series") },
            label = { Text("Series") },
            selected = navController.currentDestination?.route == "series",
            onClick = { navController.navigate("series") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Acteurs") },
            label = { Text("Acteurs") },
            selected = navController.currentDestination?.route == "acteurs",
            onClick = { navController.navigate("acteurs") }
        )
    }
}



