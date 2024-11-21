package com.example.premiereappli

import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Tmdbresult

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Tmdbresult

    @GET("tv/popular")
    suspend fun getPopularSeries(@Query("api_key") apiKey: String): Tmdbresult

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") apiKey: String, @Query("query") query: String): Tmdbresult

    @GET("search/person")
    suspend fun searchPersons(@Query("api_key") apiKey: String, @Query("query") query: String): Tmdbresult
}
