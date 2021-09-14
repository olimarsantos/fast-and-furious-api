package com.olimar.fastandfuriousapi.controller

import com.olimar.fastandfuriousapi.model.entity.*
import com.olimar.fastandfuriousapi.service.MovieService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1")
class FastAndFuriousController(private val movieService: MovieService) {

    @GetMapping("/movie-complete/{id}")
    fun findMovieById(@PathVariable id: String): ResponseEntity<Movie?> {
        movieService.fetchMovieById(id)?.let { return ResponseEntity.ok(it) }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/movie/{id}")
    fun findMovieInfoById(@PathVariable id: String): ResponseEntity<MovieInfo?> {
        movieService.fetchMovieById(id)?.let { return ResponseEntity.ok(it.toResponse()) }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/movie")
    fun saveMovie(@RequestBody movie: List<Movie>): ResponseEntity<List<Movie>> {
        movieService.saveMovies(movie).let { return ResponseEntity(it, HttpStatus.CREATED) }
    }

    @GetMapping("/catalog")
    fun findCatalog(): ResponseEntity<List<Catalog>> {
        val catalog = movieService.fetchCatalog()
        if (catalog.isNotEmpty()) {
            return ResponseEntity.ok(catalog)
        }
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/catalog")
    fun saveCatalog(@RequestBody catalog: Catalog): ResponseEntity<Catalog> {
        movieService.saveCatalog(catalog).let { return ResponseEntity(it, HttpStatus.CREATED) }
    }

    @PostMapping("/movie-times")
    fun saveMovieTimes(@RequestBody movieTime: MovieTime): ResponseEntity<MovieTime> {
        movieService.saveMovieTime(movieTime).let { return ResponseEntity(it, HttpStatus.CREATED) }
    }

    @PutMapping("/movie-times/{id}")
    fun updateMovieTimes(@PathVariable id: UUID, @RequestBody movieTime: MovieTime): ResponseEntity<MovieTime> {
        val movieTimeOld = movieService.fetchMovieTimeById(id)
        if (movieTimeOld.isPresent) {
            movieService.updateMovieTime(movieTime).let { return ResponseEntity.ok(it) }
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/movie-times")
    fun findMovieTimes(): ResponseEntity<List<MovieTime>> {
        val movieTimes = movieService.fetchMovieTime()
        if (movieTimes.isNotEmpty()) {
            return ResponseEntity.ok(movieTimes)
        }
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/review/{imdbID}")
    fun findReview(@PathVariable imdbID: String): ResponseEntity<List<Review?>> {
        movieService.fetchReviews(imdbID).let { return ResponseEntity.ok(it) }
    }

    @PostMapping("/review")
    fun saveReview(@RequestBody reviewRequest: Review): ResponseEntity<Review?> {
        movieService.saveReview(reviewRequest).let { return ResponseEntity(it, HttpStatus.CREATED) }
    }

    private fun Movie.toResponse(): MovieInfo {
        return MovieInfo(
            title = this.title,
            genre = this.genre,
            plot = this.plot,
            released = this.released,
            ratings = this.ratings
        )
    }
}