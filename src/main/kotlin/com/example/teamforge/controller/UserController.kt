package com.example.teamforge.controller

import com.example.teamforge.auth.JWTProvider
import com.example.teamforge.entity.*
import com.example.teamforge.model.request.LoginRequestDto
import com.example.teamforge.model.request.ProfileUpdateDto
import com.example.teamforge.model.request.SignupRequestDto
import com.example.teamforge.repository.TagRepository
import com.example.teamforge.repository.UserExperienceRepository
import com.example.teamforge.repository.UserRepository
import com.example.teamforge.util.ApiResponse
import com.example.teamforge.util.toUuid
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("user")
class UserController(
    val jwtProvider: JWTProvider,
    val userRepository: UserRepository,
    val userExperienceRepository: UserExperienceRepository,
    val tagRepository: TagRepository
) {

    @PostMapping("/register")
    fun register(
        @Valid @RequestBody userReq: SignupRequestDto
    ): ResponseEntity<ApiResponse> {

        if (userRepository.existsByEmail(userReq.email)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse(false, "Email is already in use", null))
        }

        val user = userRepository.save(
            UserEntity(
                name = userReq.name,
                password = userReq.password,
                email = userReq.email,
                instituteName = userReq.instituteName
            )
        ).toDto()

        val token = jwtProvider.generate("id" to user.id.toString())
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                ApiResponse(
                    true,
                    "User registered successfully",
                    object {
                        val user = user
                        val token = token
                    }
                )
            )
    }

    @PostMapping("/login")
    fun login(
        @Valid @RequestBody loginRequestDto: LoginRequestDto
    ): ResponseEntity<ApiResponse> {
        val user = userRepository.findByEmail(loginRequestDto.email!!)
        if (user.isPresent && user.get().password == loginRequestDto.password) {
            val token = jwtProvider.generate("id" to user.get().id.toString())
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    ApiResponse(
                        true,
                        "User logged in successfully",
                        object {
                            val user = user.get().toDto()
                            val token = token
                        }
                    )
                )
        }
        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .body(ApiResponse(false, "Invalid credentials", null))
    }

    @GetMapping("/me")
    fun me(
        @RequestHeader("Authorization") token: String
    ) = ResponseEntity
        .status(HttpStatus.OK)
        .body(
            ApiResponse(
                true,
                "User details",
                userRepository.findUserEntitiesById(getUserIdFromToken(token, jwtProvider)).get().toDto()
            )
        )

    @PostMapping("/profile")
    fun profile(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody profileUpdateDto: ProfileUpdateDto
    ): ResponseEntity<ApiResponse> {
        val user = userRepository.findUserEntitiesById(getUserIdFromToken(token, jwtProvider)).get()
        userExperienceRepository.saveAll(
            profileUpdateDto.experience.map {
                UserExperienceEntity.fromDto(it, user)
            }
        )
        val tags = profileUpdateDto.tagIds.mapNotNull { tagRepository.findById(it.toUuid()).orElse(null) }.toSet()
        user.tags = tags
        user.role = profileUpdateDto.title
        userRepository.save(user)

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(
                ApiResponse(
                    true,
                    "Profile updated successfully",
                    user.toDto()
                )
            )
    }

    @GetMapping("/tags")
    fun getUsersByTags(
        @RequestBody tagIds: List<String>
    ): ResponseEntity<ApiResponse> {
        val tags = tagIds.mapNotNull { tagRepository.findById(it.toUuid()).orElse(null) }
        val users = userRepository.findUsersByTags(tags.toSet())
        return ResponseEntity
            .ok(
                ApiResponse(
                    true,
                    "Users fetched successfully",
                    users.map { it.toDto() }
                )
            )
    }

    @GetMapping("/invitations")
    fun getInvitations(
        @RequestHeader("Authorization") token: String
    ): ResponseEntity<ApiResponse> {
        val user = userRepository.findUserEntitiesById(getUserIdFromToken(token, jwtProvider)).get()
        return ResponseEntity
            .ok(
                ApiResponse(
                    true,
                    "Invitations fetched successfully",
                    user.teams.filter { !it.invitationAccepted }.map { it.team.toDto() }
                )
            )
    }

    @GetMapping("/profile")
    fun getProfile(
        @RequestParam userId: String
    ): ResponseEntity<ApiResponse> {
        val user = userRepository.findById(userId.toUuid()).orElseThrow { throw Exception("User not found") }
        return ResponseEntity
            .ok(
                ApiResponse(
                    true,
                    "Profile fetched successfully",
                    user.toDto()
                )
            )
    }

}



fun getUserIdFromToken(token: String, jwtProvider: JWTProvider): UUID = jwtProvider.decode(token)["id"]!!.toUuid()