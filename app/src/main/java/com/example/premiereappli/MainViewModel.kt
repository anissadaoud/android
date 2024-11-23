package com.example.premiereappli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    // StateFlow pour les acteurs et les films
    val actors: MutableStateFlow<List<TmdbPerson>> = MutableStateFlow(listOf())
    val movies: MutableStateFlow<List<TmdbMovie>> = MutableStateFlow(listOf())
    val series: MutableStateFlow<List<TmdbSeries>> = MutableStateFlow(listOf())
    // Clé API pour TMDb
    private val apikey = "a6f34ffd317094fe364b44e6dbd6d5bc"

    // Création du service Retrofit pour l'API
    private val service: TmdbAPI = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbAPI::class.java)

    // Fonction pour récupérer les acteurs populaires
    fun getPopularActors() {
        viewModelScope.launch {
            val response = service.getPopularActors(apikey)
            actors.value = response.results // Met à jour la liste des acteurs
        }
    }

    // Fonction pour récupérer les films populaires
    fun getPopularMovies() {
        viewModelScope.launch {
            val response = service.getPopularMovies(apikey)
            movies.value = response.results // Met à jour la liste des films
        }
    }

    // Fonction pour récupérer les séries populaires
    fun getPopularSeries() {
        viewModelScope.launch {
            val response = service.getPopularSeries(apikey)
            series.value = response.results // Met à jour la liste des séries populaires
        }
    }

    // Fonction pour rechercher des films en fonction du mot-clé
    fun searchMovies(motcle: String){
        viewModelScope.launch{
            movies.value = service.searchMovies(apikey, motcle).results
        }
    }

    // Fonction pour rechercher des acteurs en fonction du mot-clé
    fun searchActors(motcle: String) {
        viewModelScope.launch {
            actors.value = service.searchPersons(apikey, motcle).results
        }
    }

    // Fonction pour rechercher des séries en fonction du mot-clé
    fun searchSeries(motcle: String) {
        viewModelScope.launch {
            series.value = service.searchSeries(apikey, motcle).results
        }
    }
}
