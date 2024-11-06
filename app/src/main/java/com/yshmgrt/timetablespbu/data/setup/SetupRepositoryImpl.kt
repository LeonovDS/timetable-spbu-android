package com.yshmgrt.timetablespbu.data.setup

import android.util.Log
import arrow.core.Either
import com.yshmgrt.timetablespbu.Configuration
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.Programme
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.model.TimetableDraft
import com.yshmgrt.timetablespbu.persistance.Timetable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

data class University(
    val url: String = Configuration.BASE_URL
)

class SetupRepositoryImpl @Inject constructor() : SetupRepository {
    override suspend fun getFacultyList(university: University): Either<Error, List<Faculty>> =
        withContext(Dispatchers.IO) {
            Either.catch {
                Jsoup.connect(university.url).cookies(mapOf("_culture" to "ru")).get()
                    .getElementsByClass("panel-default").first()
                    ?.getElementsByTag("a")?.map {
                        Faculty(
                            faculty = it.text(),
                            url = Configuration.BASE_URL + it.attr("href")
                        )
                    } ?: emptyList()
            }.mapLeft {
                Error.UnknownException(it)
            }
        }

    override suspend fun getGroupList(faculty: Faculty): Either<Error, List<ProgrammeGroup>> =
        withContext(Dispatchers.IO) {
            Either.catch {
                Jsoup.connect(faculty.url).cookies(mapOf("_culture" to "ru")).get()
                    .getElementsByClass(
                        "panel-default"
                    ).map {
                        ProgrammeGroup(
                            name = it.getElementsByClass("panel-title").text(),
                            programmes = it.getElementsByTag("li").map { programme ->
                                val programmeName = programme.child(0).text()
                                Programme(
                                    name = programmeName,
                                    groups = programme.getElementsByTag("a").map {
                                        Log.d(
                                            "SetupRepo",
                                            it.attributes().asList().map { it.toString() }
                                                .joinToString(" --- ")
                                        )
                                        Group(
                                            faculty = faculty.faculty,
                                            programme = programmeName,
                                            group = it.attr("title"),
                                            url = Configuration.BASE_URL + it.attr("href"),
                                            year = it.text()
                                        )
                                    }
                                )
                            }.filter {
                                it.groups.isNotEmpty()
                            }
                        )
                    }
            }.mapLeft {
                Error.UnknownException(it)
            }
        }

    override suspend fun getTimetableList(group: Group): Either<Error, List<Timetable>> =
        withContext(Dispatchers.IO) {
            Either.catch {
                Jsoup.connect(group.url).cookies(mapOf("_culture" to "ru")).get()
                    .getElementsByClass("panel-default").map {
                        Timetable(
                            url = Configuration.BASE_URL + (it.getElementsByClass("tile").first()
                                ?.attribute("onclick")?.value?.substringAfter("'")
                                ?.substringBefore("'")
                                ?: ""),
                            faculty = group.faculty,
                            group = group.group,
                            year = group.year,
                            programme = group.programme,
                            timetable = it.getElementsByClass("panel-title").first()?.text() ?: "",
                        )
                    }
            }.mapLeft {
                Error.UnknownException(it)
            }
        }
}