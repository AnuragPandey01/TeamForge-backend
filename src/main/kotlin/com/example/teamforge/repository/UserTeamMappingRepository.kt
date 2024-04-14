package com.example.teamforge.repository

import com.example.teamforge.entity.TeamEntity
import com.example.teamforge.entity.UserEntity
import com.example.teamforge.entity.UserTeamMappingEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserTeamMappingRepository : JpaRepository<UserTeamMappingEntity, UUID>{
    fun findByUserAndTeam(user: UserEntity, team: TeamEntity): UserTeamMappingEntity?
    fun findByTeam(team: TeamEntity): List<UserTeamMappingEntity>
}