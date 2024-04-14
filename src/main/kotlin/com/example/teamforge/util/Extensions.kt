package com.example.teamforge.util

import java.util.*

fun String.toUuid() = UUID.fromString(this)