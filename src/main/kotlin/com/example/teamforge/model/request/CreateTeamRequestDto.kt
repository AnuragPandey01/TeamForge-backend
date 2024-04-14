package com.example.teamforge.model.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CreateTeamRequestDto(

    @field:NotBlank(message = "name is required")
    @field:NotNull(message = "name is required")
    val name:String? = null,

    val description:String? = "",
)