package com.yshmgrt.timetablespbu.data

import android.util.Log
import com.yshmgrt.timetablespbu.model.Lesson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atTime
import kotlinx.datetime.toLocalDateTime
import org.jsoup.Jsoup
import javax.inject.Inject

class RemoteTimetableRepositoryImpl @Inject constructor() : RemoteTimetableRepository {
    override suspend fun getTimetable(url: String, date: LocalDate): List<Lesson> =
        withContext(Dispatchers.IO) {
            Jsoup.connect("$url/$date").cookies(mapOf("_culture" to "ru")).get()
                .getElementsByClass("panel-default").flatMap {
                    val dateString = it.getElementsByClass("panel-title").text()
                    val date = parseDate(dateString)
                    it.getElementsByTag("li").map {
                        val timeString = it.getElementsByClass("studyevent-datetime").text()
                        val (begin, end) = timeString.split('–')
                        Lesson(
                            name = it.getElementsByClass("studyevent-subject").text(),
                            startTime = date.atTime(parseTime(begin)),
                            endTime = date.atTime(parseTime(end)),
                            place = it.getElementsByClass("studyevent-locations").text(),
                            teacher = it.getElementsByClass("studyevent-educators").text(),
                            isCanceled = it.getElementsByClass("cancelled").isNotEmpty()
                        )
                    }
                }
        }

    private fun parseDate(date: String, language: Language = Language.RU): LocalDate {
        return when (language) {
            Language.EN -> parseEnDate(date)
            Language.RU -> parseRuDate(date)
        }
    }

    private fun parseEnDate(date: String): LocalDate {
        Log.d("parseDate", date)
        val (_, month, day) = date.split(" ")
        return LocalDate(
            year = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            monthNumber = mapOf(
                "January" to 1,
                "February" to 2,
                "March" to 3,
                "April" to 4,
                "May" to 5,
                "June" to 6,
                "July" to 7,
                "August" to 8,
                "September" to 9,
                "October" to 10,
                "November" to 11,
                "December" to 12
            )[month] ?: 1,
            dayOfMonth = day.toInt()
        )
    }

    private fun parseRuDate(date: String): LocalDate {
        val (_, day, month) = date.split(" ")
        return LocalDate(
            year = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year,
            monthNumber = mapOf(
                "января" to 1,
                "февраля" to 2,
                "марта" to 3,
                "апреля" to 4,
                "мая" to 5,
                "июня" to 6,
                "июля" to 7,
                "августа" to 8,
                "сентября" to 9,
                "октября" to 10,
                "ноября" to 11,
                "декабря" to 12
            )[month] ?: 1,
            dayOfMonth = day.toInt()
        )
    }

    private fun parseTime(time: String): LocalTime {
        val (hour, minute) = time.split(":")
        return LocalTime(hour = hour.toInt(), minute = minute.toInt())
    }
}

enum class Language {
    EN, RU
}