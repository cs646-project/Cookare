package com.example.cookare.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.net.Uri.fromFile
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
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
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.amplifyframework.core.Amplify
import com.example.cookare.model.Recipe
import com.example.cookare.ui.MainActivity
import com.example.cookare.ui.theme.CookareTheme
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
    var content by mutableStateOf(if (recipe != null) recipe.content else "")
    var tags by mutableStateOf(recipe?.tags?.toString() ?: "")
    var updateUser by mutableStateOf(if (recipe != null) recipe.updateUser else "")
    var coverUrl by mutableStateOf(if (recipe != null) recipe.coverUrl else "")
    var ingredientName1 by mutableStateOf("")
    var ingredientNum1 by mutableStateOf("")
    var ingredientName2 by mutableStateOf("")
    var ingredientNum2 by mutableStateOf("")

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

                // bug: first check imageUri
                if (imageUri != null) {
                    Log.i("Take Photo", "from gallery1")
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

                        Log.i("Take Photo", "from gallery2")
                    }
                }
                else if (takenFromCamera) {
                    Log.i("Take Photo", "from camera1")

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

                        Log.i("Take Photo", "from gallery2")
                    }
                }
                else {
                    Log.i("Take Photo", "from default1")
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

                Text(
                    "Ingredients",
                    Modifier
                        .padding(14.dp, 24.dp, 14.dp, 14.dp),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                if (ingredients.isNotEmpty()) {
                    for (i in ingredients) {
                        if (i.isNotEmpty()) {
                            i.forEachIndexed { index, d ->
                                d.name?.let {
                                    OutlinedTextField(
                                        value = it,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth(),
                                        label = { Text(text = "Ingredient name -- ${index + 1}") },
                                        onValueChange = {

                                        }
                                    )

                                    OutlinedTextField(
                                        value = d.num.toString(),
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .fillMaxWidth(),
                                        label = { Text(text = "Ingredient number -- ${index + 1}") },
                                        onValueChange = {
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        if (recipe != null) {
                            imageUri?.let {
                                recipe.coverUrl?.let { it1 ->
                                    viewModel.postRecipe(
                                        Recipe(
                                            recipeId,
                                            title,
                                            content,
                                            Integer.parseInt(tags),
                                            userId,
                                            uploadPhotoUri(it, it1, context),
                                            null
                                        )
                                    )
                                }
                            }

                            bitmap?.let { btm ->
                                recipe.coverUrl?.let {
                                    viewModel.postRecipe(
                                        Recipe(
                                            recipeId,
                                            title,
                                            content,
                                            Integer.parseInt(tags),
                                            userId,
                                            uploadPhotoBitmap(btm, it, context),
                                            null
                                        )
                                    )
                                }
                            }
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
                                    null
                                )
                            )
                        }

                        var intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
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
    coverUrl: String,
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
    coverUrl: String,
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
