package com.yshmgrt.timetablespbu.model

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Lesson(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val place: String,
    val teacher: String,
    val isCanceled: Boolean = false
)