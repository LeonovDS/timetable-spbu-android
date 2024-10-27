package com.yshmgrt.timetablespbu.ui.main.settings

import kotlinx.serialization.Serializable

sealed interface SettingsDestination {
    @Serializable
    data object Main : SettingsDestination

    @Serializable
    data object Timetable : SettingsDestination

    @Serializable
    data object Ban : SettingsDestination
}