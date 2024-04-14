package com.example.teamforge.entity

import com.example.teamforge.model.response.UserResponseDto
import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.*
import org.springframework.data.jpa.domain.AbstractPersistable_.id

@Entity
@Table(name = "users")
class UserEntity(

    @Column(name = "name", nullable = false)
    var name:String,

    @Column(name = "email", nullable = false, unique = true)
    var email:String,

    @Column(name = "password", nullable = false)
    var password:String,

    @Column(name = "role")
    var role:String? = "developer",

    @Column(name = "institute_name", nullable = true)
    var instituteName:String? = null,

    @ManyToMany
    @JoinTable(
        name = "user_tag",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "tag_id")]
    )
    @JsonBackReference
    var tags : Set<TagEntity> = emptySet(),

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    var teams : Set<UserTeamMappingEntity> = emptySet(),

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    var experiences : Set<UserExperienceEntity> = emptySet(),

    @OneToMany(mappedBy = "postedBy")
    @JsonManagedReference
    var posts:Set<EventEntity> = emptySet(),

    @Column(name = "icon_url")
    var iconUrl: String = "https://api.dicebear.com/8.x/identicon/jpeg?seed=${email}"

): BaseEntity(){

    fun toDto(): UserResponseDto {
        return UserResponseDto(
            id = id!!,
            name = name,
            email = email,
            role = role,
            instituteName = instituteName,
            tags = tags.map { it.toDto() }.toSet(),
            teams = teams.map { it.toDto() }.toSet(),
            experiences = experiences.map { it.toDto() }.toSet(),
            iconUrl = iconUrl
        )
    }
}
