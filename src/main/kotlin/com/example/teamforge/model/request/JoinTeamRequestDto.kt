package com.example.teamforge.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.UUID

data class JoinTeamRequestDto(

    @field:NotBlank(message = "Team ID is required")
    @field:NotNull(message = "Team ID is required")
    @UUID(message = "Team ID must be a valid UUID")
    val teamId: String

)

data class InviteUserRequestDto(

    @field:NotBlank(message = "User ID is required")
    @field:NotNull(message = "User ID is required")
    @UUID(message = "User ID must be a valid UUID")
    val userId: String,

    @field:NotBlank(message = "Team ID is required")
    @field:NotNull(message = "Team ID is required")
    @UUID(message = "Team ID must be a valid UUID")
    val teamId: String

)

data class AcceptTeamInviteRequestDto(

    @field:NotBlank(message = "Team ID is required")
    @field:NotNull(message = "Team ID is required")
    @UUID(message = "Team ID must be a valid UUID")
    val teamId: String

)