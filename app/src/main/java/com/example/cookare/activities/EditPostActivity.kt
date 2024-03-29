package com.example.cookare.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.cookare.R
import com.example.cookare.model.Ingredient
import coil.compose.rememberAsyncImagePainter
import com.amplifyframework.core.Amplify
import com.example.cookare.model.Recipe
import com.example.cookare.ui.MainActivity
import com.example.cookare.ui.auth
import com.example.cookare.ui.home.isNumber
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.green000
import com.example.cookare.ui.userId
import com.example.cookare.viewModels.PostRecipeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class EditPostActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchId = Integer.parseInt(intent.getStringExtra("id"))
        Log.d("searchId", "recipeId $searchId")

        setContent {
            CookareTheme {
                EditRecipeScreen(searchId, hiltViewModel())
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EditRecipeScreen(
    recipeId: Int,
    viewModel: PostRecipeViewModel
) {
    viewModel.searchById(listOf(recipeId))
    var data = viewModel.resRecipeByIdList.value
    val recipes = data.map { it.recipe }
    val ingredients = data.map { it.ingredients }
    var recipe = if (recipes.isNotEmpty()) recipes[0] else null

    var title by mutableStateOf(if (recipe != null) recipe.title else "")
    var content by  mutableStateOf(if (recipe != null) recipe.content else "")
    var tags by  mutableStateOf(recipe?.tags?.toString() ?: "")
    var updateUser by  mutableStateOf(if (recipe != null) recipe.updateUser else "")
    var coverUrl by mutableStateOf(if (recipe != null) recipe.coverUrl else "")

    var num by mutableStateOf(if(ingredients.isNotEmpty()) ingredients[0].size else 0)
    var count by remember { mutableStateOf(0)}

    var ingredientNameMap: MutableMap<String, MutableState<String?>> = remember { mutableMapOf() }
    var ingredientNumMap: MutableMap<String, MutableState<String>> = remember { mutableMapOf() }
    for (index in 1..(num+count)) {
        ingredientNameMap["ingredientName$index"] = remember { mutableStateOf( if (index <= num && ingredients[0][index-1].name != null) ingredients[0][index-1].name else "") }
        ingredientNumMap["ingredientNum$index"] = remember { mutableStateOf(if(index <= num && ingredients[0][index-1].num.toString() != "null") ingredients[0][index-1].num.toString() else "") }
    }

    val context = LocalContext.current

    val bottomSheetModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    val localFile = File("${context.filesDir}/photo.png")

    var photoUri by remember {
        mutableStateOf<Uri>(
            FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                localFile
            )
        )
    }

    var isCameraSelected by remember {
        mutableStateOf(false)
    }

    var takenFromCamera by remember {
        mutableStateOf(false)
    }

    val galleryLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
        takenFromCamera = false
    }

    val cameraLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.TakePicture()) {
            result: Boolean  ->
        if (result) {
            takenFromCamera = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
        if(isGranted){
            if (isCameraSelected){
                cameraLauncher.launch(photoUri)
            }else{
                galleryLauncher.launch("image/*")
            }

            coroutineScope.launch {
                bottomSheetModalState.hide()
            }
        }else{
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    ModalBottomSheetLayout(sheetContent = {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(CookareTheme.colors.primary.copy(0.08f))
        ){
            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                androidx.compose.material.Text(
                    text = "Add Photo!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    color = CookareTheme.colors.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                androidx.compose.material.Divider(modifier = Modifier
                    .height(1.dp)
                    .background(CookareTheme.colors.primary)
                )
                androidx.compose.material.Text(
                    text = "Take Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) -> {
                                    cameraLauncher.launch(photoUri)
                                    coroutineScope.launch {
                                        bottomSheetModalState.hide()
                                    }
                                }
                                else -> {
                                    isCameraSelected = true
                                    permissionLauncher.launch((Manifest.permission.CAMERA))
                                }
                            }
                        }
                        .padding(15.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
                androidx.compose.material.Divider(modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                )
                androidx.compose.material.Text(
                    text = "Choose from Gallery",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.READ_EXTERNAL_STORAGE
                                ) -> {
                                    galleryLauncher.launch("image/*")
                                    coroutineScope.launch {
                                        bottomSheetModalState.hide()
                                    }
                                }
                                else -> {
                                    isCameraSelected = false
                                    permissionLauncher.launch((Manifest.permission.READ_EXTERNAL_STORAGE))
                                }
                            }
                        }
                        .padding(15.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
                androidx.compose.material.Divider(modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                )
                androidx.compose.material.Text(
                    text = "Cancel",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            coroutineScope.launch {
                                bottomSheetModalState.hide()
                            }
                        }
                        .padding(15.dp),
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
        }
    },
        sheetState = bottomSheetModalState,
        sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
        modifier = Modifier.background(CookareTheme.colors.background)
    ) {

        LazyColumn(state = rememberLazyListState()) {
            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .background(color = CookareTheme.colors.onPrimaryContainer),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedButton(
                            onClick = {
                                var intent = Intent(context, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                                intent.putExtra("token", auth)
                                intent.putExtra("id", userId)
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .size(60.dp)
                                .padding(12.dp),
                            shape = CircleShape,
                            border = BorderStroke(1.5.dp, Color.White),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "go back",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(30.dp),
                                tint = Color.White
                            )
                        }

                        Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White,
                                    )
                                ) {
                                    append("Edit Your Recipe")
                                }
                            }
                        )
                    }
                }

                Text(
                    "Recipe detail ",
                    Modifier
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                title?.let { it1 ->
                    OutlinedTextField(
                        value = it1,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Title") },
                        placeholder = { Text(text = "") },
                        onValueChange = {
                            title = it
                        }
                    )
                }

                content?.let { it1 ->
                    OutlinedTextField(
                        value = it1,
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        label = { Text(text = "Content") },
                        onValueChange = {
                            content = it
                        }
                    )
                }

                OutlinedTextField(
                    value = tags,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    label = { Text(text = "Tags") },
                    onValueChange = {
                        tags = it
                    }
                )

                androidx.compose.material.Button(
                    onClick = {
                        coroutineScope.launch {
                            if(!bottomSheetModalState.isVisible){
                                bottomSheetModalState.show()
                            }else{
                                bottomSheetModalState.hide()
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    androidx.compose.material.Text(
                        text = "Take Picture",
                        modifier = Modifier.padding(8.dp),
                        textAlign = TextAlign.Center,
                        color =  Color.White
                    )
                }

                if (!takenFromCamera && imageUri != null) {
                    Log.i("Take Photo", "from gallery")
                    if(!isCameraSelected){
                        bitmap = if(Build.VERSION.SDK_INT<28){
                            MediaStore.Images.Media.getBitmap(context.contentResolver,imageUri)
                        }else{
                            val source = ImageDecoder.createSource(context.contentResolver,
                                imageUri!!
                            )
                            ImageDecoder.decodeBitmap(source)
                        }
                    }

                    bitmap?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "Image",
                            alignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 10.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.High
                        )
                    }
                }
                else if (takenFromCamera) {
                    Log.i("Take Photo", "from camera")

                    bitmap = if(Build.VERSION.SDK_INT<28){
                        MediaStore.Images.Media.getBitmap(context.contentResolver,photoUri)
                    }else{
                        val source = ImageDecoder.createSource(context.contentResolver,
                            photoUri!!
                        )
                        ImageDecoder.decodeBitmap(source)
                    }

                    bitmap?.let { btm ->
                        Image(
                            bitmap = btm.asImageBitmap(),
                            contentDescription = "Image",
                            alignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(top = 10.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.High
                        )
                    }
                }
                else {
                    Log.i("Take Photo", "from default")
                    coverUrl?.let {
                        val downloadedImage = downloadPhoto(coverUrl!!, context)
                        Image(
                            painter = rememberAsyncImagePainter(downloadedImage),
                            contentDescription = "Recipe Featured Image",
                            Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .fillMaxWidth()
                                .aspectRatio(1.35f),
                            contentScale = ContentScale.Fit,
                            alignment = Alignment.TopCenter
                        )
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        "Ingredients",
                        Modifier
                            .padding(14.dp, 24.dp, 14.dp, 14.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedButton(
                        onClick = {
                            count += 1
                        },
                        modifier = Modifier
                            .size(30.dp),
                        shape = CircleShape,
                        border = BorderStroke(1.5.dp, green000),
                        colors = ButtonDefaults.buttonColors(green000),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_add),
                            contentDescription = "add ingredients",
                            modifier = Modifier
                                .padding(6.dp)
                                .size(60.dp),
                            tint = Color.White
                        )
                    }
                }


                for (index in 1..(num+count)) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        ingredientNameMap["ingredientName$index"]!!.value?.let { it1 ->
                            OutlinedTextField(
                                value = it1,
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f),
                                label = { Text(text = "Name$index") },
                                placeholder = { Text(text = "") },
                                onValueChange = {
                                    ingredientNameMap["ingredientName$index"]!!.value = it
                                }
                            )
                        }

                        OutlinedTextField(
                            value = ingredientNumMap["ingredientNum$index"]!!.value,
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f),
                            label = { Text(text = "Number$index") },
                            placeholder = { Text(text = "") },
                            onValueChange = {
                                ingredientNumMap["ingredientNum$index"]!!.value = it
                            }
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (!takenFromCamera && imageUri != null) {
                            viewModel.postRecipe(
                                Recipe(
                                    recipeId,
                                    title,
                                    content,
                                    Integer.parseInt(tags),
                                    userId,
                                    uploadPhotoUri(imageUri!!, context),
                                    (1..num+count).map{
                                        Ingredient(
                                            if(ingredientNameMap["ingredientName$it"]?.value != "") ingredientNameMap["ingredientName$it"]!!.value else "",
                                            if(ingredientNumMap["ingredientNum$it"]?.value != "" && isNumber(ingredientNumMap["ingredientNum$it"]?.value)) Integer.parseInt(ingredientNumMap["ingredientNum$it"]!!.value) else 0
                                        )
                                    }.filter {
                                        it.name != ""
                                    }.filter {
                                        it.num != 0
                                    }
                                )
                            )
                        }
                        else if(takenFromCamera) {
                            viewModel.postRecipe(
                                Recipe(
                                    recipeId,
                                    title,
                                    content,
                                    Integer.parseInt(tags),
                                    userId,
                                    uploadPhotoUri(photoUri!!, context),
                                    (1..num+count).map{
                                        Ingredient(
                                            if(ingredientNameMap["ingredientName$it"]?.value != "") ingredientNameMap["ingredientName$it"]!!.value else "",
                                            if(ingredientNumMap["ingredientNum$it"]?.value != "" && isNumber(ingredientNumMap["ingredientNum$it"]?.value)) Integer.parseInt(ingredientNumMap["ingredientNum$it"]!!.value) else 0
                                        )
                                    }.filter {
                                        it.name != ""
                                    }.filter {
                                        it.num != 0
                                    }
                                )
                            )
                        }
                        else {
                            viewModel.postRecipe(
                                Recipe(
                                    recipeId,
                                    title,
                                    content,
                                    Integer.parseInt(tags),
                                    userId,
                                    coverUrl,
                                    (1..num+count).map{
                                        Ingredient(
                                            if(ingredientNameMap["ingredientName$it"]?.value != "") ingredientNameMap["ingredientName$it"]!!.value else "",
                                            if(ingredientNumMap["ingredientNum$it"]?.value != "" && isNumber(ingredientNumMap["ingredientNum$it"]?.value)) Integer.parseInt(ingredientNumMap["ingredientNum$it"]!!.value) else 0
                                        )
                                    }.filter {
                                        it.name != ""
                                    }.filter {
                                        it.num != 0
                                    }
                                )
                            )
                        }

                        var intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                        intent.putExtra("token", auth)
                        intent.putExtra("id", userId)
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ) {
                    Text(text = "Edit")
                }
            }
        }
    }
}


private fun uploadPhotoUri(
    imageUri: Uri,
    context: Context
):String {
    val newUrl = UUID.randomUUID().toString()
    val photoKey = "$newUrl.png"
    val stream = context.contentResolver.openInputStream(imageUri)!!
    Log.i("uploadPhotoUri", "Uri photoKey is: $photoKey")

    Amplify.Storage.uploadInputStream(
        photoKey,
        stream,
        { },
        { error -> Log.e("uploadPhotoUri", "Failed upload", error) }
    )

    return newUrl
}

private fun uploadPhotoBitmap(
    imageBitmap: Bitmap,
    context: Context
):String {
    val newUrl = UUID.randomUUID().toString()
    val photoKey = "$newUrl.png"
    val filePath = "${context.filesDir}/${photoKey}"
    Log.i("uploadPhotoBitmap", "Bitmap photoKey is: $photoKey")
    Log.i("uploadPhotoBitmap", "filePath is: $filePath")

    val localFile = File("${context.filesDir}/${photoKey}")

    try {
        val saveImgOut = FileOutputStream(localFile)
        imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, saveImgOut)
        // flush and close the output stream
        saveImgOut.flush()
        saveImgOut.close()
        Log.i("uploadPhotoBitmap", "The picture is saved!")
    } catch (ex: IOException) {
        ex.printStackTrace()
    }

    Amplify.Storage.uploadFile(
        photoKey,
        localFile,
        { },
        { Log.e("uploadPhotoBitmap", "Failed upload", it) }
    )

    return newUrl
}

private fun downloadPhoto(
    coverUrl: String,
    context: Context
): File {
    val photoKey = "$coverUrl.png"
    val filePath = "${context.filesDir}/${photoKey}"

    Log.i("downloadPhoto", "download photoKey is: $photoKey")

    if (!fileIsExists(filePath)) {
        val localFile = File(filePath)
        Amplify.Storage.downloadFile(
            photoKey,
            localFile,
            { },
            { Log.e("downloadPhoto", "Failed download", it) }
        )
    }

    return File(filePath)
}

private fun fileIsExists(filePath: String): Boolean {
    try {
        val f = File(filePath)
        if (!f.exists()) {
            return false
        }
    } catch (e: Exception) {
        return false
    }
    return true
}
