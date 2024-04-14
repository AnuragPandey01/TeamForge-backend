package com.example.teamforge.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "user_team_mapping")
class UserTeamMappingEntity(

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @ManyToOne
    @JsonManagedReference
    @JoinColumn(name = "team_id")
    var team : TeamEntity,

    @Column(name="is_leader", nullable = false)
    var isLeader: Boolean = false,

    var invitationAccepted: Boolean = false,

    var invitationDenied : Boolean = false,

    var requestToJoin : Boolean = false
) : BaseEntity(){

        companion object{
            fun fromDto(dto: UserTeamMappingDto, user: UserEntity, team: TeamEntity): UserTeamMappingEntity{
                return UserTeamMappingEntity(
                    user = user,
                    team = team,
                    isLeader = dto.isLeader
                )
            }
        }

        fun toDto(): UserTeamMappingResponse{
            return UserTeamMappingResponse(
                userId = user,
                teamId = team.id!!,
                isLeader = isLeader,
                invitationAccepted = invitationAccepted
            )
        }

}

data class UserTeamMappingDto(
    val userId: String,
    val teamId: String,
    val isLeader: Boolean
)

data class UserTeamMappingResponse(
    val userId: UserEntity,
    val teamId: UUID,
    val isLeader: Boolean,
    val invitationAccepted: Boolean
)
