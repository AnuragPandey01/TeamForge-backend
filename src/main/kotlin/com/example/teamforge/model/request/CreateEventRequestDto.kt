package com.example.teamforge.model.request

import com.example.teamforge.entity.EVENT_TYPE
import com.example.teamforge.entity.PARTICIPATION_TYPE
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.springframework.format.annotation.DateTimeFormat
import java.util.*

data class CreateEventRequestDto(

    @field:NotBlank(message = "Title is mandatory")
    @field:NotNull(message = "Title is mandatory")
    var title:String? = null,

    @field:NotBlank(message = "Description is mandatory")
    @field:NotNull(message = "Description is mandatory")
    var description: String? = null,

    @field:NotNull(message = "Start date is mandatory")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var startDate: Date? = null,

    @field:NotNull(message = "End date is mandatory")
    @field:DateTimeFormat(pattern = "yyyy-MM-dd")
    var endDate: Date? =  null,

    @field:NotNull(message = "Event type is mandatory")
    var eventType: EVENT_TYPE,

    var link:String,

    @field:NotNull(message = "Is online is mandatory")
    var isOnline:Boolean,

    var location:String? = null,

    @field:NotNull(message = "Event type is mandatory")
    var participationType: PARTICIPATION_TYPE,

    var teamSize:Int? = null
)