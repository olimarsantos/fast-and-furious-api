package com.olimar.fastandfuriousapi.repository

import com.olimar.fastandfuriousapi.model.entity.MovieTime
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MovieTimeRepository : JpaRepository<MovieTime, UUID>