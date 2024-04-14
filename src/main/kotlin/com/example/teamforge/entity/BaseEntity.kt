package com.example.teamforge.entity

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.util.*

@MappedSuperclass
open class BaseEntity (

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)