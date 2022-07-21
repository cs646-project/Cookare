package com.example.cookare.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.model.StockMap
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.FunctionalGrey
import com.example.cookare.ui.theme.green200
import com.example.cookare.ui.theme.yel1
import com.example.cookare.viewModels.StockViewModel


@Composable
fun TodoItem(
    key: String,
    value: Int,
    stockViewModel: StockViewModel
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    val (count, updateCount) = remember { mutableStateOf(value.toInt()) }

    Card(
        backgroundColor = green200,
        modifier = Modifier
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.size(16.dp))
            Column(modifier = Modifier.weight(25f)) {
                Text(text = key,
//                    style = MaterialTheme.typography.subtitle2,
                    color = FunctionalGrey,
                    style = MaterialTheme.typography.button,
                    fontSize = 20.sp,)
                Spacer(modifier = Modifier.size(20.dp))
//                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                    Text(text = value.toString(), style = MaterialTheme.typography.body2)
//                }
                QuantitySelector(
                    count = count,
                    decreaseItemCount = { if (count > 0) updateCount(count - 1) },
                    increaseItemCount = { updateCount(count + 1) }
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(
                onClick = {
                    showDeleteDialog = true
                }
            ) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = null)
            }
        }

        if (showDeleteDialog) {
            AlertDialog(
                onDismissRequest = { showDeleteDialog = false },
                title = { androidx.compose.material3.Text(text = "Delete") },
                text = { androidx.compose.material3.Text(text = "Are you sure to delete this stock?") },
                buttons = {
                    Row() {
                        TextButton(
                            onClick = {
                                showDeleteDialog = false
                                stockViewModel.deleteStock(key)
                            }
                        ) {
                            androidx.compose.material3.Text(text = "Yes")
                        }
                        TextButton(
                            onClick = { showDeleteDialog = false }
                        ) {
                            androidx.compose.material3.Text(text = "Cancel")
                        }
                    }

                },
                modifier = Modifier.background(BackgroundWhite)
            )
        }

    }
}