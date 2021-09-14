package com.olimar.fastandfuriousapi.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import javax.persistence.*

@Entity(name = "MOVIE")
@JsonFormat(with = [JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES])
data class Movie(
    @Id
    val imdbID: String,
    val actors: String,
    val awards: String,
    val boxOffice: String,
    val country: String,
    val dvd: String,
    val director: String,
    val genre: String,
    val language: String,
    val metascore: String,
    val plot: String,
    val poster: String,
    val production: String,
    val rated: String,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "rating_id")
    val ratings: List<Rating>,
    val released: String,
    val response: String,
    val runtime: String,
    val title: String,
    val type: String,
    val website: String,
    val writer: String,
    val year: String,
    val imdbRating: String,
    val imdbVotes: String
)

data class MovieInfo(
    val title: String,
    val genre: String,
    val plot: String,
    val released: String,
    val ratings: List<Rating>
)