package com.olimar.fastandfuriousapi.repository

import com.olimar.fastandfuriousapi.model.entity.Catalog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CatalogRepository : JpaRepository<Catalog, UUID>