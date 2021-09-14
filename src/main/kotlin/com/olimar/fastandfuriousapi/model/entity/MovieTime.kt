package com.olimar.fastandfuriousapi.model.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "MOVIE_TIMES")
data class MovieTime(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val title: String,
    val movieTime: String,
    val price: Double
)