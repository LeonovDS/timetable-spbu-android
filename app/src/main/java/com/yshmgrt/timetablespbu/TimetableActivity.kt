package com.yshmgrt.timetablespbu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.yshmgrt.timetablespbu.ui.theme.TimetableSPBUTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TimetableActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TimetableSPBUTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Text("Hello, World!", modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
