package com.example.teamforge.repository

import com.example.teamforge.entity.TagEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TagRepository : JpaRepository<TagEntity,UUID>