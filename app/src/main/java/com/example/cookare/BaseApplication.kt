package com.example.cookare

import android.app.Application
import com.example.cookare.ui.component.list.Graph
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication :Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}