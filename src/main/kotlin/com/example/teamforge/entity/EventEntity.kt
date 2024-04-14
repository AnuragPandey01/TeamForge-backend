package com.example.teamforge.entity

import com.example.teamforge.model.request.CreateEventRequestDto
import com.example.teamforge.model.response.UserResponseDto
import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import org.springframework.format.annotation.DateTimeFormat
import java.util.Date

@Entity
@Table(name="events")
class EventEntity(

    @Column(name="title", nullable = false)
    var title:String,

    @Column(name="description", nullable = false,columnDefinition = "TEXT")
    var description: String,

    @Column(name="start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var startDate:Date,

    @Column(name="end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    var endDate: Date,

    @Column(name="event_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var eventType:EVENT_TYPE,

    @Column(name="link", nullable = false)
    var link:String,

    @Column(name="is_online", nullable = false)
    var isOnline:Boolean,

    @Column(name="location", nullable = true)
    var location:String? = null, //nullable because it is not required for online

    @Column(name="participation_type", nullable = false)
    @Enumerated(EnumType.STRING)
    var participationType:PARTICIPATION_TYPE,

    @Column(name="team_size", nullable = true)
    var teamSize:Int? = null, //nullable because it is not required for individual events

    @ManyToOne
    @JoinColumn(name="posted_by", nullable = false)
    @JsonBackReference
    var postedBy:UserEntity

): BaseEntity(){

    companion object{
        fun fromDto(dto: CreateEventRequestDto, user: UserEntity): EventEntity{
            return EventEntity(
                title = dto.title!!,
                description = dto.description!!,
                startDate = dto.startDate!!,
                endDate = dto.endDate!!,
                eventType = dto.eventType,
                link = dto.link,
                isOnline = dto.isOnline,
                location = dto.location,
                participationType = dto.participationType,
                teamSize = dto.teamSize,
                postedBy = user
            )
        }
    }

    fun toDto(): EventResponseDto {
        return EventResponseDto(
            title = title,
            description = description,
            startDate = startDate,
            endDate = endDate,
            eventType = eventType,
            link = link,
            isOnline = isOnline,
            location = location,
            participationType = participationType,
            teamSize = teamSize,
            postedBy = postedBy.toDto()
        )
    }
}

data class EventResponseDto(
    var title:String,
    var description: String,
    var startDate:Date,
    var endDate: Date,
    var eventType:EVENT_TYPE,
    var link:String,
    var isOnline:Boolean,
    var location:String? = null,
    var participationType:PARTICIPATION_TYPE,
    var teamSize:Int? = null,
    var postedBy: UserResponseDto
)

enum class PARTICIPATION_TYPE{
    INDIVIDUAL,
    TEAM
}

enum class EVENT_TYPE{
    HACKATHON,
    SEMINAR,
    WORKSHOP
}