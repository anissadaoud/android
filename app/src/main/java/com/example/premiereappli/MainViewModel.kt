package com.example.premiereappli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.playlistjson
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    // StateFlow
    val actors: MutableStateFlow<List<TmdbPerson>> = MutableStateFlow(listOf())
    val movies: MutableStateFlow<List<TmdbMovie>> = MutableStateFlow(listOf())
    val series: MutableStateFlow<List<TmdbSeries>> = MutableStateFlow(listOf())
    val movieDetail = MutableStateFlow<TmdbMovieDetails?>(null)
    val movieCast : MutableStateFlow<List<Cast>> = MutableStateFlow(listOf())
    val seriesDetail = MutableStateFlow<TmdbSeriesDetails?>(null)
    val seriesCast : MutableStateFlow<List<Cast>> = MutableStateFlow(listOf())
    var playlist : MutableStateFlow<List<Playlist>> = MutableStateFlow(listOf())
    // Clé API pour TMDb
    private val apikey = "a6f34ffd317094fe364b44e6dbd6d5bc"

    // Création du service Retrofit pour l'API
    val service: TmdbAPI = Retrofit.Builder()
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


    // Fonction pour récupérer les détails d'un film
    fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            val response = service.getMovieDetail(movieId.toString(), apikey)
            movieDetail.value = response
        }
    }

    // Fonction pour récupérer les détails d'une série
    fun getSeriesDetail(seriesId: Int) {
        viewModelScope.launch {
            val response = service.getSeriesDetail(seriesId.toString(), apikey)
            seriesDetail.value = response
        }
    }

    fun getFilmCast(MovieId: Int) {
        viewModelScope.launch {
            try {
                val response = service.getMovieCast(MovieId,apikey).cast
                movieCast.value = response
            } catch (e: Exception) {
                movieCast.value
            }
        }
    }

    fun getSerieCast(seriesId: Int) {
        viewModelScope.launch {
            try {
                val response = service.getSeriesCast(seriesId,apikey).cast
                seriesCast.value = response
            } catch (e: Exception) {
                seriesCast.value
            }
        }
    }

    // récupère la playlist
    fun fetchPlaylist() : Playlist {
     val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        return moshi.adapter(Playlist::class.java).fromJson(playlistjson)!!
    }

    fun getPlaylist() {
        viewModelScope.launch {
            playlist.value = listOf(fetchPlaylist())
        }
    }

}


