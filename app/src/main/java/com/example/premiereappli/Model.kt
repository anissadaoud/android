package com.example.premiereappli
data class Tmdbresult(
    val page: Int,
    val results: List<TmdbMovie>,
    val total_pages: Int,
    val total_results: Int
)

data class TmdbMovie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
data class TmdbSeries(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val first_air_date: String,
    val vote_average: Double,
    val vote_count: Int,
    val genre_ids: List<Int>,
    val backdrop_path: String
)

data class TmdbPerson(
    val id: Int,
    val name: String,
    val popularity: Double,
    val profile_path: String,
    val known_for: List<KnownFor>
)

data class KnownFor(
    val id: Int,
    val title: String?,
    val name: String?,
    val overview: String,
    val media_type: String,
    val vote_average: Double,
    val vote_count: Int,
    val poster_path: String,
    val backdrop_path: String
)
data class TmdbMovieDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val release_date: String,
    val runtime: Int?,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val crew: List<Crew>,
    val poster_path: String,
    val backdrop_path: String,
    val vote_average: Double,
    val vote_count: Int
)

data class Genre(
    val id: Int,
    val name: String
)

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profile_path: String
)

data class Crew(
    val id: Int,
    val name: String,
    val job: String,
    val profile_path: String
)


