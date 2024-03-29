package com.example.cookare.ui

import android.icu.number.IntegerWidth
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.CompositionLocalProvider
import com.amplifyframework.AmplifyException
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin
import com.amplifyframework.core.Amplify
import com.amplifyframework.storage.s3.AWSS3StoragePlugin
import com.example.cookare.ui.home.UserState
import com.example.cookare.ui.home.UserStateViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.properties.Delegates

lateinit var auth: String
var userId: Int? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val token = intent.getStringExtra("token")
        if (token != null) {
            auth = "satoken=$token"
        }

        val id = intent.getStringExtra("id")
        userId = id?.let { Integer.parseInt(it) }

        configureAmplify()

        setContent {
                CookareApp()
        }
    }

    private fun configureAmplify() {
        try {
            Amplify.addPlugin(AWSCognitoAuthPlugin())
            Amplify.addPlugin(AWSS3StoragePlugin())
            Amplify.configure(applicationContext)

            Log.i("kilo", "Initialized Amplify")
        } catch (error: AmplifyException) {
            Log.e("kilo", "Could not initialize Amplify", error)
        }
    }
}


