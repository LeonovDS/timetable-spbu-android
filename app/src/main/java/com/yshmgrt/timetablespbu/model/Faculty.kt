package com.yshmgrt.timetablespbu.model

import kotlinx.serialization.Serializable

@Serializable
data class Faculty(
    val faculty: String,
    val url: String
)