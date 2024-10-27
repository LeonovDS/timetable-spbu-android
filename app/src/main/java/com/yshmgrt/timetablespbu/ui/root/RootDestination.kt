package com.yshmgrt.timetablespbu.ui.root

import kotlinx.serialization.Serializable

sealed interface RootDestination {
    @Serializable
    data object Splash : RootDestination

    @Serializable
    data object Setup : RootDestination

    @Serializable
    data object Timetable : RootDestination
}
