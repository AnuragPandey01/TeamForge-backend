package com.example.teamforge.repository

import com.example.teamforge.entity.TagEntity
import com.example.teamforge.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface UserRepository : JpaRepository<UserEntity, UUID> {

    fun findByEmail(email: String): Optional<UserEntity>{
        return this.findAll().stream().filter { it.email == email }.findFirst()
    }

    fun findUserEntitiesById(id:UUID) : Optional<UserEntity>

    @Query("SELECT u FROM UserEntity u JOIN u.tags t WHERE t IN :tags")
    fun findUsersByTags(@Param("tags") tags:Set<TagEntity>): List<UserEntity>

    fun existsByEmail(email: String): Boolean
}