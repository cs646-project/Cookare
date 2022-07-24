package com.example.cookare.ui.food

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.ui.components.TodoItem

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.R
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.BackgroundWhite
import com.example.cookare.ui.theme.green000
import com.example.cookare.ui.theme.green200
import com.example.cookare.viewModels.StockViewModel


@Composable
fun FoodScreen(
    stockViewModel: StockViewModel,
    navController: NavController,
    onNavigate: (Todo?) -> Unit
) {
    stockViewModel.getStock()
//    val data = stockViewModel.resStockList.value

    Scaffold(floatingActionButton = {
        FloatingActionButton(backgroundColor = green000,
            onClick = { onNavigate(null) }
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_add),
                contentDescription = "add_food",
                modifier = Modifier
                    .padding(10.dp)
                    .size(32.dp),
                tint = BackgroundWhite
            )
        }
    }) {
        Column() {
            if(stockViewModel.resStockList.value.isEmpty()){
                Card(
                    backgroundColor = green200,
                    modifier = Modifier
                        .padding(16.dp)
                ){
                    Spacer(modifier = Modifier.size(16.dp))
                    Column(modifier = Modifier.weight(25f)){
                        androidx.compose.material.Text(
                            text = "Please add stock here!!!",
                            color = Color.Black,
                            style = MaterialTheme.typography.button,
                            fontSize = 20.sp,
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                    }
                }
            }else{
                for(entry in stockViewModel.resStockList.value.entries.iterator()){
                    TodoItem(
                        key = entry.key,
                        value = entry.value,
                        stockViewModel = stockViewModel,
                        navController = navController
                    )
                }
            }
        }
    }
}