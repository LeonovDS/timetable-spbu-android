package com.yshmgrt.timetablespbu.ui.setup

import kotlinx.serialization.Serializable

sealed interface SetupDestination {
    @Serializable
    data object FacultySelect : SetupDestination

    @Serializable
    data class GroupSelect(val url: String) : SetupDestination

    @Serializable
    data class TimetableSelect(val url: String) : SetupDestination
}