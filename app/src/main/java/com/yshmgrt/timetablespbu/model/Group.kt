package com.yshmgrt.timetablespbu.model

import kotlinx.serialization.Serializable

@Serializable
data class Group(
    val faculty: String,
    val programme: String,
    val group: String,
    val url: String,
    val year: String
)