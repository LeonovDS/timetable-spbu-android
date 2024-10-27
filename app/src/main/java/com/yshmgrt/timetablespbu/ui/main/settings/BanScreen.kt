package com.yshmgrt.timetablespbu.ui.main.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yshmgrt.timetablespbu.R

@Composable
fun BanScreen(
    banViewModel: BanViewModel,
    modifier: Modifier = Modifier
) {
    val bans = banViewModel.bans.collectAsState()
    LaunchedEffect(Unit) {
        banViewModel.loadBans()
    }
    if (bans.value.isEmpty()) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Пусто")
        }
    }

    LazyColumn(
        modifier = modifier
            .padding(8.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(bans.value) {
            Card(
                Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        Modifier.weight(1.0f, false)
                    ) {
                        Text(it.name, overflow = TextOverflow.Ellipsis, maxLines = 2)
                        Text(
                            it.startTime.date.dayOfWeek.toString() + " " + it.startTime.time.toString(),
                            fontWeight = FontWeight.Light,
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        { banViewModel.unban(it) },
                    ) {
                        Icon(
                            painterResource(R.drawable.outline_visibility_24),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}
