package com.example.teamforge.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "teams")
class TeamEntity(

    @Column(name = "name", nullable = false)
    var name:String? =  null,

    @Column(name = "team_size", nullable = false)
    var teamSize:Int? = null,

    @OneToMany(mappedBy = "team")
    @JsonBackReference
    var members:Set<UserTeamMappingEntity> = emptySet()

): BaseEntity(){

    fun toDto(): TeamResponseDto{
        return TeamResponseDto(
            id = id!!,
            name = name!!,
            teamSize = teamSize!!
        )
    }

}

data class TeamResponseDto(
    val id: UUID,
    val name:String,
    val teamSize:Int
)