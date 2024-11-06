package com.yshmgrt.timetablespbu.ui.setup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yshmgrt.timetablespbu.ui.component.SPBULogo

@ExperimentalMaterial3Api
@Composable
fun SetupScreen(
    isLoading: Boolean = false,
    onNext: (() -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    Scaffold(topBar = {
        TopAppBar(title = { Text(text = "Timetable") })
    }) { padding ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top)
            ) {
                Icon(SPBULogo, contentDescription = "")
                content()
                Row(Modifier.align(Alignment.CenterHorizontally)) {
                    Button(
                        enabled = onNext != null,
                        onClick = onNext ?: {},
                    ) {
                        Text("Далее")
                    }
                }
            }
            if (isLoading) {
                CircularProgressIndicator(
                    Modifier
                        .width(24.dp)
                        .height(24.dp)
                )
            }
        }
    }
}
