package com.olimar.fastandfuriousapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.olimar.fastandfuriousapi.model.entity.*
import com.olimar.fastandfuriousapi.repository.CatalogRepository
import com.olimar.fastandfuriousapi.repository.MovieRepository
import com.olimar.fastandfuriousapi.repository.MovieTimeRepository
import com.olimar.fastandfuriousapi.repository.ReviewRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest
@AutoConfigureMockMvc
class FastAndFuriousControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var movieRepository: MovieRepository

    @Autowired
    lateinit var reviewRepository: ReviewRepository

    @Autowired
    lateinit var movieTimeRepository: MovieTimeRepository

    @Autowired
    lateinit var catalogRepository: CatalogRepository

    @Test
    fun `find movie by id with success`() {
        val movies = movieRepository.saveAll(createMovies())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie/${movies[0].imdbID}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.genre").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.plot").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.released").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.ratings[0].source").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.ratings[0].value").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `find movie by id with invalid id`() {
        movieRepository.saveAll(createMovies())
        val invalidId = "asdfgh"

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie/$invalidId"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[ID] must have 9 characters"))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `find movie by id with not found id`() {
        movieRepository.saveAll(createMovies())
        val noContentId = "rrddee400"

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie/$noContentId"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `find complete movie info by id with success`() {
        val movies = movieRepository.saveAll(createMovies())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie-complete/${movies[0].imdbID}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.genre").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.plot").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.released").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.ratings[0].source").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.ratings[0].value").isString)
            .andDo(MockMvcResultHandlers.print())
    }

    @Test
    fun `test create movies with success`() {
        val movies = createMovies()
        val json = ObjectMapper().writeValueAsString(movies)
        movieRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/movie")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].title").value(movies[0].title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].genre").value(movies[0].genre))
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].plot").value(movies[0].plot))
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].released").value(movies[0].released))
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].ratings[0].source").value(movies[0].ratings[0].source))
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].ratings[0].value").value(movies[0].ratings[0].value))
            .andDo(MockMvcResultHandlers.print())

        Assertions.assertTrue(movieRepository.findAll().isNotEmpty())
    }

    @Test
    fun `test find reviews with success`() {
        val review = reviewRepository.save(createReview())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/review/${review.imdbID}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].id").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].imdbID").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].score").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].comment").isString)
    }

    @Test
    fun `test create review with success`() {
        val review = createReview()
        val json = ObjectMapper().writeValueAsString(review)
        reviewRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/review")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.imdbID").value(review.imdbID))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.score").value(review.score))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.comment").value(review.comment))

        Assertions.assertTrue(reviewRepository.findAll().isNotEmpty())

    }

    @Test
    fun `test create review with invalid score`() {
        val review = createReview().copy(score = 10)
        val json = ObjectMapper().writeValueAsString(review)
        reviewRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/review")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").isNumber)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.statusCode").value(400))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.message").value("[score] score must be between 0 and 5"))

        Assertions.assertTrue(reviewRepository.findAll().isEmpty())

    }

    @Test
    fun `test create movie time with success`() {
        val movieTime = createMovieTime()
        val json = ObjectMapper().writeValueAsString(movieTime)
        movieTimeRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/movie-times")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(movieTime.title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.movieTime").value(movieTime.movieTime))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.price").value(movieTime.price))

        Assertions.assertTrue(reviewRepository.findAll().isNotEmpty())
    }

    @Test
    fun `test find movie times with success`() {
        movieTimeRepository.save(createMovieTime())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie-times"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].title").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].movieTime").isString)
    }

    @Test
    fun `test find movie times with no content status`() {
        movieTimeRepository.deleteAll()

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/movie-times"))
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `test update movie time with success`() {
        val movieTime = movieTimeRepository.save(createMovieTime()).copy(title = "New title updated")
        val json = ObjectMapper().writeValueAsString(movieTime)
        mockMvc.perform(
            MockMvcRequestBuilders.put("/v1/movie-times/${movieTime.id}")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(movieTime.title))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.movieTime").value(movieTime.movieTime))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.price").value(movieTime.price))
            .andDo(MockMvcResultHandlers.print())

        val findById = movieTimeRepository.findById(movieTime.id!!)
        Assertions.assertTrue(findById.isPresent)
        Assertions.assertEquals(movieTime.title, findById.get().title)
    }

    @Test
    fun `test create catalog with success`() {
        val catalog = createCatalog()
        val json = ObjectMapper().writeValueAsString(catalog)
        catalogRepository.deleteAll()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/v1/catalog")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.jsonPath("\$.imdbID").value(catalog.imdbID))
            .andExpect(MockMvcResultMatchers.jsonPath("\$.title").value(catalog.title))

        Assertions.assertTrue(catalogRepository.findAll().isNotEmpty())
    }

    @Test
    fun `test find catalog with success`() {
        catalogRepository.save(createCatalog())

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/catalog"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].imdbID").isString)
            .andExpect(MockMvcResultMatchers.jsonPath("\$[0].title").isString)
    }


    fun createMovies(): List<Movie> {
        return listOf(
            Movie(
                title = "The Fast and the Furious",
                year = "2001",
                rated = "PG-13",
                released = "22 Jun 2001",
                runtime = "106 min",
                genre = "Action, Crime, Thriller",
                director = "Rob Cohen",
                writer = "Ken Li, Gary Scott Thompson, Erik Bergquist",
                actors = "Vin Diesel, Paul Walker, Michelle Rodriguez",
                plot = "Los Angeles police officer Brian O'Conner must decide where his loyalty really lies when he becomes enamored with the street racing world he has been sent undercover to destroy.",
                language = "English, Spanish",
                country = "United States, Germany",
                awards = "11 wins & 18 nominations",
                poster = "https://m.media-amazon.com/images/M/MV5BNzlkNzVjMDMtOTdhZC00MGE1LTkxODctMzFmMjkwZmMxZjFhXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1_SX300.jpg",
                ratings = listOf(
                    Rating(
                        id = UUID.randomUUID(),
                        source = "Internet Movie Database",
                        value = "6.8/10"
                    ),
                    Rating(
                        id = UUID.randomUUID(),
                        source = "Metacritic",
                        value = "58/100"
                    )
                ),
                metascore = "58",
                imdbRating = "6.8",
                imdbVotes = "366,093",
                imdbID = "tt0232500",
                type = "movie",
                dvd = "03 Jun 2003",
                boxOffice = "$144,533,925",
                production = "Original Film, Neal H. Moritz Productions, Universal Pictures",
                website = "N/A",
                response = "True",
            ),
            Movie(
                title = "Furious 6",
                year = "2013",
                rated = "PG-13",
                released = "24 May 2013",
                runtime = "130 min",
                genre = "Action, Crime, Thriller",
                director = "Justin Lin",
                writer = "Chris Morgan, Gary Scott Thompson",
                actors = "Vin Diesel, Paul Walker, Dwayne Johnson",
                plot = "Hobbs has Dominic and Brian reassemble their crew to take down a team of mercenaries: Dominic unexpectedly gets sidetracked with facing his presumed deceased girlfriend, Letty.",
                language = "English, Spanish, Russian, Japanese, Cantonese, Dutch",
                country = "United States, Japan, Spain, United Kingdom",
                awards = "10 wins & 22 nominations",
                poster = "https://m.media-amazon.com/images/M/MV5BMTM3NTg2NDQzOF5BMl5BanBnXkFtZTcwNjc2NzQzOQ@@._V1_SX300.jpg",
                ratings = listOf(
                    Rating(
                        id = UUID.randomUUID(),
                        source = "Internet Movie Database",
                        value = "7.0/10"
                    ),
                    Rating(
                        id = UUID.randomUUID(),
                        source = "Rotten Tomatoes",
                        value = "71%"
                    ),
                    Rating(
                        id = UUID.randomUUID(),
                        source = "Metacritic",
                        value = "61/100"
                    )
                ),
                metascore = "61",
                imdbRating = "7.0",
                imdbVotes = "380,054",
                imdbID = "tt1905041",
                type = "movie",
                dvd = "29 Oct 2013",
                boxOffice = "$238,679,850",
                production = "Original Film, One Race Productions",
                website = "N/A",
                response = "True",
            )
        )
    }

    fun createReview(): Review {
        return Review(
            id = UUID.randomUUID(),
            imdbID = "tt0232500",
            score = 5,
            comment = "Excellent!"
        )
    }

    fun createMovieTime(): MovieTime {
        return MovieTime(
            id = UUID.randomUUID(),
            title = "Fast & Furious 6",
            movieTime = "2021-12-01T15:00:00Z",
            price = 10.50
        )
    }

    fun createCatalog(): Catalog {
        return Catalog(
            imdbID = "tt0232500",
            title = "The Fast and the Furious"
        )
    }

}