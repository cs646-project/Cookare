package com.example.cookare.ui.food

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cookare.model.Recipe
import com.example.cookare.ui.utils.DEFAULT_RECIPE_IMAGE
import com.example.cookare.ui.utils.ScreenRoute
import com.example.cookare.ui.utils.loadPicture
import com.example.cookare.viewModels.PostRecipeViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeCard(
    recipe: Recipe,
//    navController: NavController,
//    viewModel: PostRecipeViewModel
    onClick: () -> Unit
) {
    Card(
//        onClick = onClick,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .padding(
                bottom = 10.dp,
                top = 10.dp,
            )
            .fillMaxWidth()
            .clickable(
                onClick = onClick
//                viewModel.searchById(listOf(recipe.id) as List<Int>)
            ),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(
                top = 16.dp,
                bottom = 16.dp,
                start = 20.dp,
                end = 20.dp
            )
        ) {
            recipe.coverUrl?.let { url ->
                val image = loadPicture(url = url, defaultImage = DEFAULT_RECIPE_IMAGE).value
                image?.let { img ->
                    Image(
                        bitmap = img.asImageBitmap(),
                        contentDescription = "Recipe Featured Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(225.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }

            recipe.title?.let { title ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 12.dp)
                ) {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start),
                        style = typography.h5
                    )
//                    Icon(
//                        imageVector = Icons.Default.StarBorder,
//                        contentDescription = "collection",
//                        modifier = Modifier
//                            .padding(end = 8.dp)
//                            .align(Alignment.CenterVertically)
//                    )
//                    Icon(
//                        imageVector = Icons.Default.FavoriteBorder,
//                        contentDescription = "like",
//                        modifier = Modifier
//                            .align(Alignment.CenterVertically)
//                    )
                }
            }

//            recipe.publisher?.let{  publisher ->
//                Column{
//                    Text(
//                        buildAnnotatedString {
//                            withStyle(
//                                style = SpanStyle(
//                                    fontStyle = FontStyle.Italic,
//                                    fontSize = 16.sp,
//                                )
//                            ) {
//                                append("Publisher: ")
//                                append(publisher)
//                            }
//                        }
//                    )
//                }
//            }
        }
    }
}
