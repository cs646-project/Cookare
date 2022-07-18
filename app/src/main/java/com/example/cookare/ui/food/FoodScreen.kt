package com.example.cookare.ui.food

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.ui.components.TodoItem

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.viewModels.StockViewModel


@Composable
fun FoodScreen(
//    stockViewModel: StockViewModel,
    onNavigate: (Todo?) -> Unit
) {
//    stockViewModel.getStock()
//    val data = stockViewModel.resStockList.value
//
//    Column() {
//        for (entry in data.entries.iterator()) {
//            Text(text = "${entry.key} : ${entry.value}")
//        }
//        Button(
//            onClick = { stockViewModel.addStock(mapOf("test1" to 1)) }
//        ) {
//            Text(text = "Add stock")
//        }
//
//        Button(
//            onClick = { stockViewModel.addStock(mapOf("test1" to 5)) }
//        ) {
//            Text(text = "Update stock")
//        }
//
//        Button(
//            onClick = { stockViewModel.deleteStock("test1") }
//        ) {
//            Text(text = "Delete stock")
//        }
//    }

    val viewModel = viewModel(HomeViewModel::class.java)
    val state by viewModel.state.collectAsState()

    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { onNavigate(null) }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }) {
        LazyColumn {
            items(state.todoList) { todo ->
                TodoItem(
                    todo = todo,
                    onChecked = { viewModel.updateTodo(it, todo.id) },
                    onDelete = { viewModel.delete(it) },
                    onNavigation = { onNavigate(it) }
                )
            }
        }
    }
}