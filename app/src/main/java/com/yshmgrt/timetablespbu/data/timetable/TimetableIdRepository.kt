package com.yshmgrt.timetablespbu.data.timetable

import arrow.core.Option
import com.yshmgrt.timetablespbu.model.LoadingState
import kotlinx.coroutines.flow.Flow

interface TimetableIdRepository {
    suspend fun setTimetableId(id: Long)
    val timetableId: Flow<LoadingState<Long>>
}
