package com.example.teamforge.util

data class ApiResponse(
    val success: Boolean,
    val message: String,
    val data: Any?
)