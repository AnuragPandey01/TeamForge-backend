package com.example.teamforge.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class SignupRequestDto(

    @field:Email(message = "Invalid email")
    @field:NotBlank(message = "Email cannot be empty")
    @field:NotNull(message = "Email cannot be null")
    val email: String,

    @field:NotBlank(message = "Name cannot be empty")
    @field:NotNull(message = "Name cannot be null")
    val name: String,

    @field:NotBlank(message = "Password cannot be empty")
    @field:NotNull(message = "Password cannot be null")
    val password: String,

    val instituteName:String?
)