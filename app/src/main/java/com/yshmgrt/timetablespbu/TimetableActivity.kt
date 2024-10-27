package com.yshmgrt.timetablespbu

import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.yshmgrt.timetablespbu.domain.InternetController
import com.yshmgrt.timetablespbu.ui.Root
import com.yshmgrt.timetablespbu.ui.theme.TimetableSPBUTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalMaterial3Api
@AndroidEntryPoint
class TimetableActivity : ComponentActivity() {
    @Inject
    lateinit var internetController: InternetController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getSystemService(ConnectivityManager::class.java).registerDefaultNetworkCallback(
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    Log.d("onAvailable", "onAvailable: $network")
                    internetController.setIsOnline(true)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    Log.d("onUnavailable", "onUnavailable")
                    internetController.setIsOnline(false)
                }
            }
        )
        setContent {
            TimetableSPBUTheme {
                Root()
            }
        }
    }
}
