package com.example.cookare.ui.food

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.example.cookare.ui.components.TodoItem
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.cookare.R
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.theme.*
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
                /*Card(
                    backgroundColor = green500,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 20.dp)
                        .height(80.dp)
                ){
                    Spacer(modifier = Modifier.size(16.dp))

                        androidx.compose.material.Text(
                            modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                            text = "Welcome to Food section!",
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                    Spacer(modifier = Modifier.size(16.dp))

                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(20.dp, 20.dp, 20.dp, 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painterResource(R.drawable.ic_555), "avatar",
                        Modifier
                            .width(180.dp)
                            .height(250.dp)
                    )

                    Column(
                        Modifier
                            .padding(horizontal = 10.dp)
                            .weight(1f)
                    ) {
                        Text("Welcome to Food section！", fontSize = 18.sp, color = Gray100,fontWeight = FontWeight.Bold)
                        Text("Scan your food using our camera below", fontSize = 18.sp, fontWeight = FontWeight.Medium)

                    }


                }

*/
                AsyncImage(
                    model = "https://i.postimg.cc/XYNnpXdK/food1.jpg",
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                )
                Divider()

                    AsyncImage(
                        model = "https://i.postimg.cc/Gmz9rKcd/food4.jpg",
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()

                    )







            }else{
                Column() {
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 20.dp, 28.dp, 0.dp),
                        text = "My stock",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    androidx.compose.material.Text(
                        modifier = Modifier.padding(25.dp, 10.dp, 28.dp, 10.dp),
                        text = "Here are the food in your fridge.",
                        fontSize = 15.sp,
                        color = Gray100
                    )
                    Divider()
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
}