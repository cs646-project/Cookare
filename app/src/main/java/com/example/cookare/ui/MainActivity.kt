package com.example.cookare.ui

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import com.example.cookare.ui.home.UserState
import com.example.cookare.ui.home.UserStateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

lateinit var auth: String
var userId: Int = 0

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("token")
        if (token != null) {
            auth = "satoken=$token"
        }

        userId = Integer.parseInt(intent.getStringExtra("id"))
        Log.d("Token", "ShowResultToken $token")
        Log.d("Auth", "ShowResultToken $auth")

        setContent {
                CookareApp()
        }
    }
}


