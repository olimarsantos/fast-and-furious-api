package com.olimar.fastandfuriousapi.repository

import com.olimar.fastandfuriousapi.model.entity.Review
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ReviewRepository : JpaRepository<Review, UUID> {
    //    fun findReviewsByImdbID(imdbId: String)
    fun findAllByImdbID(imdbId: String): List<Review>
}