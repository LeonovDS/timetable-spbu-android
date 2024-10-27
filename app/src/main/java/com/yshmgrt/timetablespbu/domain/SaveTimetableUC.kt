package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.TimetableSummary
import com.yshmgrt.timetablespbu.persistance.TimetableDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveTimetableUC @Inject constructor(
    private val timetableDAO: TimetableDAO
) {
    suspend operator fun invoke(timetable: TimetableSummary): Long = withContext(Dispatchers.IO) {
        timetableDAO.insertTimetable(
            timetable.programme,
            timetable.group,
            timetable.year,
            timetable.url
        )
    }
}

