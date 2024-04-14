package com.example.teamforge.repository

import com.example.teamforge.entity.EventEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface EventRepository : JpaRepository<EventEntity,UUID>