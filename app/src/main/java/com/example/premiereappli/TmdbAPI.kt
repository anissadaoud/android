package com.example.premiereappli

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): Tmdbresult

    @GET("search/movie")
    suspend fun searchMovies(@Query("api_key") apiKey: String, @Query("query") query: String): Tmdbresult

    @GET("tv/popular")
    suspend fun getPopularSeries(@Query("api_key") apiKey: String): SeriesResponse

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") apiKey: String, @Query("query") query: String): SeriesResponse

    @GET("search/person")
    suspend fun searchPersons(@Query("api_key") apiKey: String, @Query("query") query: String): ActorResponse


    @GET("movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: String,
        @Query("api_key") apiKey: String
    ): TmdbMovie

    @GET("person/popular")
    suspend fun getPopularActors(@Query("api_key") apiKey: String): ActorResponse
}
