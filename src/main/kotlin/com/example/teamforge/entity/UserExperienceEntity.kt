package com.example.teamforge.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
@Table(name = "user_experiences")
class UserExperienceEntity(

    @Column(name = "title", nullable = false)
    @NotBlank(message = "title is required")
    var title:String,

    @Column(name = "description", nullable = false)
    @NotBlank(message = "description is required")
    var description:String,

    @Column(name="link",nullable=true)
    var link:String? = null,

    @Column(name="start_date",nullable=false)
    var startDate: Date,

    @Column(name="end_date",nullable=false)
    var endDate: Date,

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    var user: UserEntity

): BaseEntity(){

    companion object{
        fun fromDto(dto: UserExperienceDto, user: UserEntity): UserExperienceEntity{
            return UserExperienceEntity(
                title = dto.title,
                description = dto.description,
                link = dto.link,
                startDate = dto.startDate,
                endDate = dto.endDate,
                user = user
            )
        }
    }

    fun toDto(): UserExperienceResponse{
        return UserExperienceResponse(
            id = id!!,
            title = title,
            description = description,
            link = link,
            startDate = startDate,
            endDate = endDate
        )
    }

}

data class UserExperienceDto(
    val title:String,
    val description:String,
    val link:String?,
    val startDate:Date,
    val endDate:Date
)

data class UserExperienceResponse(
    val id: UUID,
    val title:String,
    val description:String,
    val link:String?,
    val startDate:Date,
    val endDate:Date
)