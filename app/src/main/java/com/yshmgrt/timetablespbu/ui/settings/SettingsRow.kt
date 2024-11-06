package com.yshmgrt.timetablespbu.ui.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsRow(title: String, description: String, onClick: () -> Unit) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(text = title)
            Text(text = description)
        }
    }
}