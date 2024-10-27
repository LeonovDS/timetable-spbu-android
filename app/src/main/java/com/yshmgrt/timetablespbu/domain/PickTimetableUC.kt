package com.yshmgrt.timetablespbu.domain

import com.yshmgrt.timetablespbu.data.UrlRepository
import com.yshmgrt.timetablespbu.model.TimetableSummary
import javax.inject.Inject

class PickTimetableUC @Inject constructor(
    private val urlRepository: UrlRepository
) {
    suspend operator fun invoke(timetable: TimetableSummary) {
        urlRepository.setUrl(timetable.url)
    }
}