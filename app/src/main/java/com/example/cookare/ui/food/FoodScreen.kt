package com.example.cookare.ui.food

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.ui.components.TodoItem

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.model.users
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.*
import com.example.cookare.viewModels.StockViewModel
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.material.icons.filled.Camera


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
//                imageVector = Icons.Default.Camera,
//                tint = Color.Black,
                painter = painterResource(R.drawable.ic_baseline_camera_alt_24),
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
                    backgroundColor = green000,
                    modifier = Modifier
                        .padding(10.dp)
                ){
                    Spacer(modifier = Modifier.size(16.dp))
                    Column(modifier = Modifier.fillMaxWidth()){
                        Row{

                        }


                        androidx.compose.material.Text(
                            modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                            text = "Welcome Food section",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        androidx.compose.material.Text(
                            modifier = Modifier.padding(25.dp, 10.dp, 28.dp, 0.dp),
                            text = "Scan your food NOW.",
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }

                }
                Spacer(modifier = Modifier.size(20.dp))

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 20.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.ic_555), "avatar",
                        Modifier
//
                            .width(180.dp)
                            .height(250.dp)
                    )

                    Column(
                        Modifier
                            .padding(horizontal = 10.dp)
                            .weight(1f)
                    ) {
                        Text("Welcome Food section！", fontSize = 18.sp, color = Gray100,fontWeight = FontWeight.Bold)
                        Text("you can scan your food using our camera below", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text("right now!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }


                }
                Spacer(modifier = Modifier.size(20.dp))
                Image(
                    painterResource(R.drawable.ic_burger), "avatar",
                    Modifier
                        .padding(horizontal = 150.dp)
                        .width(100.dp)
                        .height(150.dp)
                )





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