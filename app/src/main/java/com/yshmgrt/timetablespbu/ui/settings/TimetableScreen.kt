package com.yshmgrt.timetablespbu.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.yshmgrt.timetablespbu.model.LoadingState
import com.yshmgrt.timetablespbu.persistance.Timetable

@Composable
fun TimetableScreen(
    timetableSettingsViewModel: TimetableSettingsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit = {},
    onTimetableSelected: (Timetable) -> Unit = {},
) {
    val currentTimetable = timetableSettingsViewModel.currentTimetable.collectAsState()
    val timetableState = timetableSettingsViewModel.timetableList.value
    val isLoading = timetableState is LoadingState.Loading
    val timetables = if (timetableState is LoadingState.Ready) timetableState.data else emptyList()
    SettingsScreen(isLoading = isLoading, onGoBack = onBackPressed) { modifier ->
        if (timetables.isEmpty()) {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Пусто")
            }
        }
        LazyColumn(modifier = modifier) {
            items(timetables) {
                ItemCard(
                    it,
                    canPick = it.id != currentTimetable.value,
                    canDelete = it.id != currentTimetable.value && timetables.size > 1,
                    {
                        timetableSettingsViewModel.deleteTimetable(it)
                    }) {
                    timetableSettingsViewModel.pickTimetable(it)
                    onTimetableSelected(it)
                }
            }
        }
    }
}

@Composable
private fun ItemCard(
    timetable: Timetable,
    canPick: Boolean,
    canDelete: Boolean,
    onDelete: (Timetable) -> Unit,
    onTimetableSelected: (Timetable) -> Unit,
) {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(timetable.programme)
            Text(
                "Группа: ${timetable.group}, ${timetable.year} год",
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.End)
            ) {
                TextButton({
                    onDelete(timetable)
                }, enabled = canDelete) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
                Button({
                    onTimetableSelected(timetable)
                }, enabled = canPick) {
                    Text("Pick")
                }
            }

        }
    }
}

@Preview
@Composable
private fun ItemCardPreview() {
    Box(
        modifier = Modifier
            .width(400.dp)
            .height(600.dp)
            .padding(8.dp)
    ) {
        ItemCard(
            Timetable(
                id = 0,
                url = "",
                timetable = "Текущий учебный год 2024-2025",
                year = "2022",
                group = "22.МО2-и",
                programme = "Прикладная информатика в области искусств и гуманитарных наук",
                faculty = "Исскуства"
            ),
            canPick = true,
            canDelete = true,
            onDelete = {}
        ) {}
    }
}