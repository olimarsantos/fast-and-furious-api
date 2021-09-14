package com.olimar.fastandfuriousapi.model.entity

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "RATING")
@JsonFormat(with = [JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES])
@JsonIgnoreProperties(ignoreUnknown = true)
data class Rating(
    @Id @GeneratedValue @JsonInclude(JsonInclude.Include.NON_NULL)
    val id: UUID?,
    val source: String,
    val value: String
)