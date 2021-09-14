package com.olimar.fastandfuriousapi.model.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "REVIEW")
data class Review(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val imdbID: String,
    val score: Int,
    val comment: String
)