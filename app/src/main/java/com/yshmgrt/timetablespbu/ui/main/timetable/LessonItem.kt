package com.yshmgrt.timetablespbu.ui.main.timetable

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yshmgrt.timetablespbu.R
import com.yshmgrt.timetablespbu.model.Lesson

@Composable
fun LessonItem(
    lesson: Lesson,
    onOpen: () -> Unit = {},
    onTemporaryBan: () -> Unit = {},
    onPermanentBan: () -> Unit = {}
) {
    var isMenuOpened by remember { mutableStateOf(false) }
    Card(modifier = Modifier
        .padding(8.dp)
        .pointerInput(lesson) {
            detectTapGestures(
                onTap = {
                    onOpen()
                },
                onLongPress = {
                    isMenuOpened = true
                }
            )
        }) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = lesson.startTime.time.toString() + "-" + lesson.endTime.time.toString(),
                modifier = Modifier
                    .fillMaxWidth(),
                fontWeight = FontWeight.Light,
                fontSize = 12.sp
            )
            Spacer(Modifier.padding(2.dp))
            Text(
                text = lesson.name,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        DropdownMenu(expanded = isMenuOpened, onDismissRequest = { isMenuOpened = false }) {
            DropdownMenuItem(
                text = { Text(text = "Hide once") },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.outline_visibility_off_24),
                        contentDescription = null
                    )
                },
                onClick = {
                    isMenuOpened = false
                    onTemporaryBan()
                }
            )
            DropdownMenuItem(
                text = { Text(text = "Hide forever") },
                leadingIcon = {
                    Icon(
                        painterResource(R.drawable.outline_visibility_off_24),
                        contentDescription = null
                    )
                },
                onClick = {
                    isMenuOpened = false
                    onPermanentBan()
                }
            )
        }
    }
}