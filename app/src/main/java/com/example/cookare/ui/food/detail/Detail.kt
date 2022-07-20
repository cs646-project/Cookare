package com.example.cookare.ui.food.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.PickPicture
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.TextFieldDefaultsMaterial
import com.example.cookare.ui.theme.green200


@Composable
fun DetailScreen(
    selectedId: Long,
    onNavigate: () -> Unit,
) {
    val viewModel = viewModel(
        DetailViewModel::class.java,
        factory = DetailViewModelFactory(selectedId)
    )
    val state by viewModel.state.collectAsState()
    DetailScreenComponent(todoText = state.todo,
        onTodoTextChange = { viewModel.onTextChange(it) },
        timeText = state.time,
        onTimeTextChange = { viewModel.onTimeChange(it) },
        onNavigate = { onNavigate() },
        onSaveTodo = { viewModel.insert(it) },
        selectedId = state.selectId)
}

@Composable
fun DetailScreenComponent(
    todoText: String,
    onTodoTextChange: (String) -> Unit,
    timeText: String,
    onTimeTextChange: (String) -> Unit,
    onNavigate: () -> Unit,
    onSaveTodo: (Todo) -> Unit,
    selectedId: Long,
) {
    val isTodoEdit = selectedId == -1L
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.size(20.dp))
       // PickPicture()
        OutlinedTextField(
            colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
            value = todoText,
            onValueChange = { onTodoTextChange(it) },
            label = { Text(text = "Enter Food name") }
        )
        Spacer(modifier = Modifier.size(16.dp))
        OutlinedTextField(
            colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
            value = timeText,
            onValueChange = { onTimeTextChange(it) },
            label = { Text(text = "Enter Number") }
        )
        Spacer(modifier = Modifier.size(25.dp))
        Button(onClick = {
            val todo = if (isTodoEdit) Todo(todoText, timeText)
            else Todo(todoText, timeText, id = selectedId)
            onSaveTodo(todo)
            onNavigate()
        },
            modifier = Modifier

            .padding(vertical = 40.dp)
            .height(50.dp).width(200.dp)
            .clip(CircleShape)

                .background(color = green200)) {
            val text = if (isTodoEdit) "Save" else "Update"
            Text(text = text)
        }


    }


}













