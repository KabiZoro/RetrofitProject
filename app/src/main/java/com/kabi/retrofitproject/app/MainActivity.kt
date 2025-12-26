package com.kabi.retrofitproject.app

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.kabi.retrofitproject.presentation.WeatherRoot
import com.kabi.retrofitproject.ui.theme.RetrofitProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                scrim = Color.TRANSPARENT,
                darkScrim = Color.TRANSPARENT
            ),
        )
        setContent {
            RetrofitProjectTheme {
                WeatherRoot()
            }
        }
    }
}