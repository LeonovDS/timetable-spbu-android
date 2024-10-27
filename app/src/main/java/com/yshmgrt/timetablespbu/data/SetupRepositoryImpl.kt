package com.yshmgrt.timetablespbu.data

import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.Group
import com.yshmgrt.timetablespbu.model.Programme
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.model.Timetable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import javax.inject.Inject

class SetupRepositoryImpl @Inject constructor() : SetupRepository {
    override suspend fun getFacultyList(url: String): List<Faculty> = withContext(Dispatchers.IO) {
        Jsoup.connect(url).cookies(mapOf("_culture" to "ru")).get().getElementsByClass("panel-default").first()
            ?.getElementsByTag("a")?.map {
                Faculty(
                    name = it.text(),
                    url = it.attr("href")
                )
            } ?: emptyList()
    }

    override suspend fun getGroupList(url: String): List<ProgrammeGroup> =
        withContext(Dispatchers.IO) {
            Jsoup.connect(url).cookies(mapOf("_culture" to "ru")).get().getElementsByClass(
                "panel-default"
            ).map {
                ProgrammeGroup(
                    name = it.getElementsByClass("panel-title").text(),
                    programmes = it.getElementsByTag("li").map {
                        Programme(
                            name = it.child(0).text(),
                            groups = it.getElementsByTag("a").map {
                                Group(
                                    name = it.attr("data-original-title"),
                                    url = it.attr("href"),
                                    year = it.text()
                                )
                            }
                        )
                    }.filter {
                        it.groups.isNotEmpty()
                    }
                )
            }
        }

    override suspend fun getTimetableList(url: String): List<Timetable> =
        withContext(Dispatchers.IO) {
            Jsoup.connect(url).cookies(mapOf("_culture" to "ru")).get().getElementsByClass("panel-default").map {
                Timetable(
                    name = it.getElementsByClass("panel-title").first()?.text() ?: "",
                    url = it.getElementsByClass("tile").first()
                        ?.attribute("onclick")?.value?.substringAfter("'")?.substringBefore("'")
                        ?: ""
                )
            }
        }
}