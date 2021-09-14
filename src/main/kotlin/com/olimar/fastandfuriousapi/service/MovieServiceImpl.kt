package com.olimar.fastandfuriousapi.service

import com.olimar.fastandfuriousapi.model.entity.Catalog
import com.olimar.fastandfuriousapi.model.entity.Movie
import com.olimar.fastandfuriousapi.model.entity.MovieTime
import com.olimar.fastandfuriousapi.model.entity.Review
import com.olimar.fastandfuriousapi.repository.CatalogRepository
import com.olimar.fastandfuriousapi.repository.MovieRepository
import com.olimar.fastandfuriousapi.repository.MovieTimeRepository
import com.olimar.fastandfuriousapi.repository.ReviewRepository
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import org.springframework.web.client.ResourceAccessException
import org.springframework.web.client.RestTemplate
import java.util.*

@Service
class MovieServiceImpl(
    private val movieRepository: MovieRepository,
    private val reviewRepository: ReviewRepository,
    private val catalogRepository: CatalogRepository,
    private val movieTimeRepository: MovieTimeRepository
) :
    MovieService {

    @Value("\${api.apiKey}")
    var apiKey: String? = null

    @Value("\${api.baseUrl}")
    var baseUrl: String? = null

    override fun fetchMovieById(id: String): Movie? {
        validateId(id)
        kotlin.runCatching {
            return RestTemplate().getForEntity("$baseUrl?apikey=$apiKey&i=$id", Movie::class.java).body
        }.onFailure { error ->
            when (error) {
                is ResourceAccessException -> {
                    val movie = movieRepository.findById(id)
                    if (movie.isPresent) {
                        return movie.get()
                    }
                    return null
                }
                else -> throw error
            }
        }
        return null
    }

    override fun saveMovies(movies: List<Movie>): List<Movie> {
        return movieRepository.saveAll(movies)
    }

    override fun fetchReviews(imdbID: String): List<Review> {
        validateId(imdbID)
        return reviewRepository.findAllByImdbID(imdbID)
    }

    override fun saveReview(review: Review): Review {
        validateScore(review.score)
        return reviewRepository.save(review)
    }

    override fun fetchCatalog(): List<Catalog> {
        return catalogRepository.findAll()
    }

    override fun saveCatalog(catalog: Catalog): Catalog {
        return catalogRepository.save(catalog)
    }

    override fun fetchMovieTime(): List<MovieTime> {
        return movieTimeRepository.findAll()
    }

    override fun fetchMovieTimeById(id: UUID): Optional<MovieTime> {
        return movieTimeRepository.findById(id)
    }

    override fun saveMovieTime(movieTime: MovieTime): MovieTime {
        return movieTimeRepository.save(movieTime)
    }

    override fun updateMovieTime(movieTime: MovieTime): MovieTime {
        return movieTimeRepository.save(movieTime)
    }

    fun validateId(id: String) {
        Assert.hasLength(id, "[ID] could not be empty")
        Assert.isTrue(id.length == 9, "[ID] must have 9 characters")
    }

    fun validateScore(score: Int) {
        Assert.isTrue(((score >= 0) and (score <= 5)), "[score] score must be between 0 and 5")
    }
}