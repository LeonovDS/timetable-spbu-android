package com.yshmgrt.timetablespbu.model

import kotlinx.serialization.Serializable

@Serializable
data class TimetableDraft(
    val group: Group,
    val name: String,
    val url: String
)