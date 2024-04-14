package com.example.teamforge.model.response

import com.example.teamforge.entity.TagResponseDto
import com.example.teamforge.entity.UserExperienceResponse
import com.example.teamforge.entity.UserTeamMappingResponse
import java.util.*

data class UserResponseDto(
    val id: UUID,
    val name:String,
    val email:String,
    val role:String?,
    val instituteName:String?,
    val tags: Set<TagResponseDto>,
    val teams: Set<UserTeamMappingResponse>,
    val experiences: Set<UserExperienceResponse>,
    val iconUrl: String
)