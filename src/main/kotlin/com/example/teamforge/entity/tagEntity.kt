package com.example.teamforge.entity

import com.fasterxml.jackson.annotation.JsonManagedReference
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import jakarta.validation.constraints.NotBlank
import java.util.*

@Entity
@Table(name = "tags")
class TagEntity(

    @Column(name = "name", nullable = false)
    @NotBlank(message = "name is required")
    var name:String,

    @ManyToMany(mappedBy = "tags")
    @JsonManagedReference
    var users : Set<UserEntity> = emptySet()

): BaseEntity(){

    companion object{
        /*fun fromDto(tagDto: TagDto): TagEntity {
            return TagEntity(
                name = tagDto.name
            )
        }*/
    }

    fun toDto(): TagResponseDto {
        return TagResponseDto(
            id = id!!,
            name = name
        )
    }
}


data class TagDto(
    val tagId : String
)

data class TagResponseDto(
    val id: UUID,
    val name: String
)