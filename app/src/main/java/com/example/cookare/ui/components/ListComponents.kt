package com.example.cookare.ui.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.green200


@Composable
fun TodoItem(
    todo: Todo,
    onDelete: (Todo) -> Unit,
    onChecked: (Boolean) -> Unit,
    onNavigation: (Todo) -> Unit,
) {
    val (count, updateCount) = remember { mutableStateOf(todo.time.toInt()) }
    Card(
        backgroundColor = green200,
        modifier = Modifier
            .padding(16.dp)
            .clickable { onNavigation(todo) },
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
//            Checkbox(checked = todo.isComplete, onCheckedChange = { onChecked(it) })
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = todo.todo,
//                    style = MaterialTheme.typography.subtitle1,
                    fontSize = 20.sp)
                Spacer(modifier = Modifier.size(16.dp))
                QuantitySelector(
                    count = count,
                    decreaseItemCount = { if (count > 0) updateCount(count - 1) },
                    increaseItemCount = { updateCount(count + 1) }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(onClick = { onDelete(todo) }) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }

        }


    }
}