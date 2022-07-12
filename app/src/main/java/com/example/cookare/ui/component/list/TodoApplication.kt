package com.example.cookare.ui.component.list

import android.app.Application

class TodoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Graph.provide(this)
    }
}