package com.example.cookare.ui.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.ui.food.NavRoute
import com.example.cookare.ui.theme.*
import com.example.cookare.viewModels.StockViewModel


@Composable
fun TodoItem(
    key: String,
    value: Int,
    stockViewModel: StockViewModel,
    navController: NavController
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var increase by remember { mutableStateOf(false) }
    var decrease by remember { mutableStateOf(false) }
//    val (count, updateCount) = remember { mutableStateOf(value.toInt()) }
    var count by  mutableStateOf(value)
//    var count by remember { mutableStateOf(value) }

    Card(
        backgroundColor = green500,
        modifier = Modifier
            .padding(20.dp,10.dp,20.dp,5.dp),
        shape = RoundedCornerShape(10.dp)
    ) {
        Spacer(modifier = Modifier.size(16.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            Column(modifier = Modifier.weight(25f)) {
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = key,
                    color = Color.White,
//                    color = FunctionalGrey,
                    style = MaterialTheme.typography.button,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(modifier = Modifier.size(10.dp))
//                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
//                    Text(text = value.toString(), style = MaterialTheme.typography.body2)
//                }
                QuantitySelector(
                    count = count,
                    decreaseItemCount = {
                        if(count > 0) count -= 1
                        decrease = true

                        Log.d("Decrease", "ShowResult: $count")
                        Log.d("DecreaseTag", "ShowResult: $decrease")
//                        stockViewModel.updateStock(key, count)
//                        Log.d("Decrease", "ShowResult: $count")
//                        if (count > 0) updateCount(count - 1)

                                        },
                    increaseItemCount = {
                        count += 1
                        increase = true
//                        stockViewModel.updateStock(key, count)
                        Log.d("Increase", "ShowResult: $count")
                        Log.d("IncreaseTag", "ShowResult: $increase")
//                        updateCount(count + 1)
                                        },
//                    stockViewModel = stockViewModel,
//                    key = key
                )
                Spacer(modifier = Modifier.size(5.dp))
            }

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
                                stockViewModel.deleteStock(key)
                                showDeleteDialog = false
                                stockViewModel.getStock()
                                navController.navigate(NavRoute.FoodScreen.route)
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

        if(increase){
            stockViewModel.updateStock(key, count)
//            stockViewModel.getStock()
            increase = false
            Log.d("IncreaseUpdate", "ShowResult: $increase")
        }

        if(decrease){
            stockViewModel.updateStock(key, count)
//            stockViewModel.getStock()
            decrease = false
            Log.d("DecreaseUpdate", "ShowResult: $decrease")
        }

    }
}