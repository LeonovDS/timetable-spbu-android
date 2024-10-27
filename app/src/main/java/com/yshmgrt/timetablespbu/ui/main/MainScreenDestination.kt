package com.yshmgrt.timetablespbu.ui.main

import kotlinx.serialization.Serializable

sealed interface MainScreenDestination {
    @Serializable
    data object Timetable : MainScreenDestination

    @Serializable
    data class Details(val json: String) : MainScreenDestination

    @Serializable
    data object Settings : MainScreenDestination
}