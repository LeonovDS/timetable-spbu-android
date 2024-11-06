package com.yshmgrt.timetablespbu.data.timetable

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import arrow.core.Option
import com.yshmgrt.timetablespbu.model.LoadingState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("timetableId")


class TimetableIdRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TimetableIdRepository {
    override suspend fun setTimetableId(id: Long) {
        Log.d(TAG, "setTimetableId: $id")
        context.dataStore.edit { it[idKey] = id }
    }

    override val timetableId: Flow<LoadingState<Long>> =
        context.dataStore.data.map {
            val id = it[idKey]
            if (id != null) {
                LoadingState.Ready(id)
            } else {
                Log.e(TAG, "getTimetableId: error: no id")
                LoadingState.Error("No Id provided")
            }
        }

    companion object {
        private val idKey = longPreferencesKey("timetableId")
        const val TAG = "TimetableIdRepository"
    }
}
