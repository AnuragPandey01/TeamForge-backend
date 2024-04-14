package com.example.teamforge.repository

import com.example.teamforge.entity.UserExperienceEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserExperienceRepository: JpaRepository<UserExperienceEntity, UUID>{
}