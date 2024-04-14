package com.example.teamforge.controller

import com.example.teamforge.auth.JWTProvider
import com.example.teamforge.entity.EventEntity
import com.example.teamforge.model.request.CreateEventRequestDto
import com.example.teamforge.repository.EventRepository
import com.example.teamforge.repository.UserRepository
import com.example.teamforge.util.ApiResponse
import com.example.teamforge.util.toUuid
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("event")
class EventController (
    val jwtProvider: JWTProvider,
    val eventRepository: EventRepository,
    val userRepository: UserRepository
){

    @PostMapping("/create")
    fun createEvent(
        @Valid @RequestBody eventReq: CreateEventRequestDto,
        @RequestHeader("Authorization") token: String
    ){
        val user = userRepository.findUserEntitiesById(jwtProvider.decode(token)["id"]!!.toUuid()).get()
        eventRepository.save(EventEntity.fromDto(eventReq,user))
    }

    @GetMapping("/all")
    fun getAllEvents() = ResponseEntity
        .ok()
        .body(
            ApiResponse(
                true,
                "All events",
                eventRepository.findAll().map { it.toDto() }
            )
        )
}