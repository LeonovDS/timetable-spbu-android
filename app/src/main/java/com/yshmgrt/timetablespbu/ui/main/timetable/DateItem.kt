package com.yshmgrt.timetablespbu.ui.main.timetable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate

@Composable
fun DateItem(date: LocalDate) {
    Row(modifier = Modifier.padding(16.dp, 8.dp)) {
        Text(
            text = "${dayOfWeekMap[date.dayOfWeek]}, ${date.dayOfMonth} ${monthMap[date.monthNumber]}",
        )
    }
}

private val dayOfWeekMap = mapOf(
    DayOfWeek(1) to "Понедельник",
    DayOfWeek(2) to "Вторник",
    DayOfWeek(3) to "Среда",
    DayOfWeek(4) to "Четверг",
    DayOfWeek(5) to "Пятница",
    DayOfWeek(6) to "Суббота",
    DayOfWeek(7) to "Воскресенье"
)

private val monthMap = mapOf(
    1 to "января",
    2 to "февраля",
    3 to "марта",
    4 to "апреля",
    5 to "мая",
    6 to "июня",
    7 to "июля",
    8 to "августа",
    9 to "сентября",
    10 to "октября",
    11 to "ноября",
    12 to "декабря"
)