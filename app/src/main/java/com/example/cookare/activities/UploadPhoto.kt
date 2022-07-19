package com.example.cookare.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.amplifyframework.core.Amplify
import com.example.cookare.model.Recipe
import com.example.cookare.ui.MainActivity
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.userId
import com.example.cookare.viewModels.UploadPhotoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


@AndroidEntryPoint
class UploadPhoto : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val searchId = intent.getStringExtra("id")?.let { Integer.parseInt(it) }
        Log.d("searchId", "recipeId $searchId")

        setContent {
            CookareTheme {
                if (searchId != null) {
                    PickPicture(searchId, hiltViewModel())
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PickPicture(
    recipeId: Int,
    viewModel: UploadPhotoViewModel
) {
    val context = LocalContext.current
    val bottomSheetModalState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    viewModel.searchById(listOf(recipeId))
    var data = viewModel.resRecipeByIdList.value
    val recipes = data.map { it.recipe }
    var recipe = if (recipes.isNotEmpty()) recipes[0] else null

    var title by mutableStateOf(if (recipe != null) recipe.title else "")
    var content by mutableStateOf(if (recipe != null) recipe.content else "")
    var tags by mutableStateOf(recipe?.tags?.toString() ?: "")

    var coverUrl by mutableStateOf(if (recipe != null) recipe.coverUrl else "")

    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var isCameraSelected by remember {
        mutableStateOf<Boolean>(false)
    }

    val galleryLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.GetContent()) { uri: Uri? ->
        imageUri = uri
    }

    val cameraLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.TakePicturePreview()){ btm: Bitmap? ->
        bitmap = btm
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract =
    ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
        if(isGranted){
            if (isCameraSelected){
                cameraLauncher.launch()
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
                Text(
                    text = "Add Photo!",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    color = CookareTheme.colors.primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif
                )
                Divider(modifier = Modifier
                    .height(1.dp)
                    .background(CookareTheme.colors.primary)
                )
                Text(
                    text = "Take Photo",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when (PackageManager.PERMISSION_GRANTED) {
                                ContextCompat.checkSelfPermission(
                                    context, Manifest.permission.CAMERA
                                ) -> {
                                    cameraLauncher.launch()
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
                Divider(modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                )
                Text(
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
                Divider(modifier = Modifier
                    .height(0.5.dp)
                    .fillMaxWidth()
                    .background(Color.LightGray)
                )
                Text(
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
        Column(
            modifier = Modifier.fillMaxSize()
        ){
            Button(
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
                Text(
                    text = "Take Picture",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    color =  Color.White
                )
            }

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
                            recipe.coverUrl?.let { viewModel.postRecipe(
                                Recipe(
                                    recipeId,
                                    title,
                                    content,
                                    Integer.parseInt(tags),
                                    userId,
                                    uploadPhotoBitmap(btm, it, context),
                                    null
                                )
                            ) }
                        }
                    }


                    val intent = Intent(context, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP;
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
                    context.startActivity(intent)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Confirm",
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center,
                    color =  Color.White
                )
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
    Log.i("uploadPhotoUri", "photoKey is: $photoKey")

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
    Log.i("uploadPhotoBitmap", "photoKey is: $photoKey")
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