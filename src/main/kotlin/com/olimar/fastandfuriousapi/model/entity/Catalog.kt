package com.olimar.fastandfuriousapi.model.entity

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity(name = "CATALOG")
data class Catalog(
    @Id
    val id: UUID? = UUID.randomUUID(),
    val imdbID: String,
    val title: String
)