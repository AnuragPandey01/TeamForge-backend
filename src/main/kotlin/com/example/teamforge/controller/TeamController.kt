package com.example.teamforge.controller

import com.example.teamforge.auth.JWTProvider
import com.example.teamforge.entity.*
import com.example.teamforge.model.request.AcceptTeamInviteRequestDto
import com.example.teamforge.model.request.CreateTeamRequestDto
import com.example.teamforge.model.request.InviteUserRequestDto
import com.example.teamforge.model.request.JoinTeamRequestDto
import com.example.teamforge.repository.TeamRepository
import com.example.teamforge.repository.UserRepository
import com.example.teamforge.repository.UserTeamMappingRepository
import com.example.teamforge.util.toUuid
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("team")
class TeamController(
    private val teamRepository: TeamRepository,
    private val jwtProvider: JWTProvider,
    private val userTeamMappingRepository: UserTeamMappingRepository,
    private val userRepository: UserRepository
) {

    @PostMapping("/create")
    fun createTeam(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody teamReq: CreateTeamRequestDto,
    ) {
        val team = teamRepository.save(
            TeamEntity(
                name = teamReq.name!!,
                teamSize = 1
            )
        )
        val userId = getUserIdFromToken(token,jwtProvider)
        val user = userRepository.findById(userId).orElseThrow { throw Exception("User not found") }
        userTeamMappingRepository.save(
            UserTeamMappingEntity(
                user = user,
                team = team,
                isLeader = true,
                invitationAccepted = true
            )
        )
    }

    @PostMapping("invite")
    fun inviteUser(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody inviteUserReq: InviteUserRequestDto,
    ) {
        val teamId = inviteUserReq.teamId.toUuid()
        val team = teamRepository.findById(teamId).orElseThrow { throw Exception("Team not found") }
        val userId = inviteUserReq.userId.toUuid()
        val user = userRepository.findById(userId).orElseThrow { throw Exception("User not found") }
        userTeamMappingRepository.save(
            UserTeamMappingEntity(
                user = user,
                team = team,
                isLeader = false,
                invitationAccepted = false,
                requestToJoin = false
            )
        )
    }

    @PostMapping("accept")
    fun acceptInvitation(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody acceptTeamInviteReq: AcceptTeamInviteRequestDto,
    ) {
        val teamId = acceptTeamInviteReq.teamId.toUuid()
        val team = teamRepository.findById(teamId).orElseThrow { throw Exception("Team not found") }
        val userId = getUserIdFromToken(token,jwtProvider)
        val user = userRepository.findById(userId).orElseThrow { throw Exception("User not found") }
        val userTeamMapping = userTeamMappingRepository.findByUserAndTeam(user, team) ?: throw Exception("User not found")
        userTeamMapping.invitationAccepted = true
        userTeamMappingRepository.save(userTeamMapping)
    }

        @PostMapping("/join")
    fun joinTeam(
        @RequestHeader("Authorization") token: String,
        @Valid @RequestBody joinTeamReq: JoinTeamRequestDto,
    ) {
        val teamId = joinTeamReq.teamId.toUuid()
        val team = teamRepository.findById(teamId).orElseThrow { throw Exception("Team not found") }
        val userId = getUserIdFromToken(token,jwtProvider)
        val user = userRepository.findById(userId).orElseThrow { throw Exception("User not found") }
        userTeamMappingRepository.save(
            UserTeamMappingEntity(
                user = user,
                team = team,
                isLeader = false,
                invitationAccepted = true
            )
        )
    }

    @PostMapping("/info")
    fun getTeamInfo(
        @RequestHeader("Authorization") token: String,
        @RequestParam teamId: String
    ): ResponseEntity<List<UserTeamMappingResponse>>{
        val team = teamRepository.findById(teamId.toUuid()).orElseThrow { throw Exception("Team not found") }

        return ResponseEntity.ok(
            userTeamMappingRepository.findByTeam(team).map { it.toDto() }
        )
    }
}