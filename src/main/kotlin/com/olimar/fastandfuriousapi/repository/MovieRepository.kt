package com.olimar.fastandfuriousapi.repository

import com.olimar.fastandfuriousapi.model.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieRepository : JpaRepository<Movie, String>