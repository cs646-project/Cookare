import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.utils.DEFAULT_RECIPE_IMAGE
import com.example.cookare.ui.utils.loadPicture

@Composable
fun RecipeCard(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .padding(15.dp)
            .clickable(onClick = onClick),
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val image = loadPicture(
                url = "https://cdn.britannica.com/17/196817-050-6A15DAC3/vegetables.jpg",
                defaultImage = DEFAULT_RECIPE_IMAGE
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Recipe Featured Image",
                    modifier = Modifier
                        .width(150.dp)
                        .height(100.dp),
                    contentScale = ContentScale.Crop,
                )
                Column(
                    modifier = Modifier
                        .padding(15.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(fontWeight = FontWeight.W900)
                            ) {
                                append(title)
                            }
                        },
                    )
                    Text(
                        buildAnnotatedString {
                            append(description)
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRecipeItem(){
    CookareTheme {
        RecipeCard("Food" ,"This is a recipe for u!", { })
    }
}












