package com.yshmgrt.timetablespbu.model

import kotlinx.datetime.LocalDateTime

data class Ban(
    val name: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val isPermanent: Boolean = false
)
