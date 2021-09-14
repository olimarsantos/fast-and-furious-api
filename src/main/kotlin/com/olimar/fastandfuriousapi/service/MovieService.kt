package com.olimar.fastandfuriousapi.service

import com.olimar.fastandfuriousapi.model.entity.Catalog
import com.olimar.fastandfuriousapi.model.entity.Movie
import com.olimar.fastandfuriousapi.model.entity.MovieTime
import com.olimar.fastandfuriousapi.model.entity.Review
import java.util.*

interface MovieService {
    fun fetchMovieById(id: String): Movie?
    fun saveMovies(movies: List<Movie>): List<Movie>

    fun fetchReviews(imdbID: String): List<Review>
    fun saveReview(review: Review): Review

    fun fetchCatalog(): List<Catalog>
    fun saveCatalog(catalog: Catalog): Catalog

    fun fetchMovieTime(): List<MovieTime>
    fun fetchMovieTimeById(id: UUID): Optional<MovieTime>
    fun saveMovieTime(movieTime: MovieTime): MovieTime
    fun updateMovieTime(movieTime: MovieTime): MovieTime
}