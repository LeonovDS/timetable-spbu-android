package com.yshmgrt.timetablespbu.data

import com.yshmgrt.timetablespbu.Configuration
import com.yshmgrt.timetablespbu.model.Faculty
import com.yshmgrt.timetablespbu.model.ProgrammeGroup
import com.yshmgrt.timetablespbu.model.Timetable

interface SetupRepository {
    suspend fun getFacultyList(url: String = Configuration.BASE_URL): List<Faculty>
    suspend fun getTimetableList(url: String): List<Timetable>
    suspend fun getGroupList(url: String): List<ProgrammeGroup>
}
