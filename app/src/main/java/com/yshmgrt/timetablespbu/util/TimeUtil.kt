package com.yshmgrt.timetablespbu.util

import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus

fun LocalDate.startOfWeek(): LocalDate {
    return this.minus(DatePeriod(days = this.dayOfWeek.isoDayNumber - 1))
}

fun LocalDate.endOfWeek(): LocalDate {
    return this.plus(DatePeriod(days = 7 - this.dayOfWeek.isoDayNumber))
}

fun LocalDate.startOfDay(): LocalDateTime {
    return this.atTime(0, 0, 0)
}

fun LocalDate.endOfDay(): LocalDateTime {
    return this.atTime(HOURS_IN_DAY - 1, MINUTES_IN_HOUR - 1, SECONDS_IN_MINUTE - 1)
}

private const val HOURS_IN_DAY = 24
private const val MINUTES_IN_HOUR = 60
private const val SECONDS_IN_MINUTE = 60
