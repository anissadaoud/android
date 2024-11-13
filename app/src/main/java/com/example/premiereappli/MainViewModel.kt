package com.example.premiereappli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainViewModel : ViewModel() {

    val movies = MutableStateFlow<List<Movie>>(listOf())

    val apikey = "a6f34ffd317094fe364b44e6dbd6d5bc"

    val service = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TmdbAPI::class.java)

    fun searchMovies(motcle: String){
        viewModelScope.launch{
            movies.value = service.getFilmsParMotCle (apikey, motcle).results

        }
    }

}