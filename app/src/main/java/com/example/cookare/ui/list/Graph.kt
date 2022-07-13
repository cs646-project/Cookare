package com.example.cookare.ui.list

import android.content.Context
import com.example.cookare.ui.list.data.TodoDataSource
import com.example.cookare.ui.list.data.room.TodoDatabase


object Graph {
    lateinit var database: TodoDatabase
        private set
    val todoRepo by lazy {
        TodoDataSource(database.todoDao())
    }

    fun provide(context: Context) {
        database = TodoDatabase.getDatabase(context)
    }
}