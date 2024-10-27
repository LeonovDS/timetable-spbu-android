package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.model.TimetableSummary
import com.yshmgrt.timetablespbu.persistance.TimetableDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetTimetableUC @Inject constructor(
    private val timetableDAO: TimetableDAO,
) {
    suspend operator fun invoke(): List<TimetableSummary> = withContext(Dispatchers.IO) {
        timetableDAO.getTimetables().map {
            TimetableSummary(
                it.programme,
                it.group,
                it.year,
                it.url
            )
        }
    }
}
