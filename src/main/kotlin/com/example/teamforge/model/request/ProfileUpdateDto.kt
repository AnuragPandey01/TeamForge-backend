package com.example.teamforge.model.request

import com.example.teamforge.entity.UserExperienceDto
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProfileUpdateDto(

    val experience : List<UserExperienceDto> = emptyList(),
    val tagIds : List<String> = emptyList(),

    @field:NotBlank(message = "Title cannot be empty")
    @field:NotNull(message = "Title cannot be null")
    val title : String? = null,
)