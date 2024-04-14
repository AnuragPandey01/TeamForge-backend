package com.example.teamforge.repository

import com.example.teamforge.entity.TeamEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface TeamRepository : JpaRepository<TeamEntity, UUID>