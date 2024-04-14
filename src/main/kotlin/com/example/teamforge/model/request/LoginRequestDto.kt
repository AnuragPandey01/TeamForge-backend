package com.example.teamforge.model.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull


data class LoginRequestDto(

    @field:Email(message = "Invalid email")
    @field:NotBlank(message = "Email cannot be empty")
    @field:NotNull(message = "Email cannot be null")
    var email: String? = null,

    @field:NotBlank(message = "Password cannot be empty")
    @field:NotNull(message = "Password cannot be null")
    var password: String? = null
)