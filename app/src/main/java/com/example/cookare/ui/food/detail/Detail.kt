package com.example.cookare.ui.food.detail

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeFile
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cookare.network.ObjectDetectAPIClient
import com.example.cookare.ui.food.data.Todo
import com.example.cookare.ui.home.isNumber
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.TextFieldDefaultsMaterial
import com.example.cookare.ui.theme.green200
import com.example.cookare.viewModels.StockViewModel
import kotlinx.coroutines.launch
import java.io.File
import kotlin.concurrent.thread


@Composable
fun DetailScreen(
    selectedId: Long,
    stockViewModel: StockViewModel,
    onNavigate: () -> Unit,
) {
    val viewModel = viewModel(
        DetailViewModel::class.java,
        factory = DetailViewModelFactory(selectedId)
    )
    val state by viewModel.state.collectAsState()
    DetailScreenComponent(
        todoText = state.todo,
        onTodoTextChange = { viewModel.onTextChange(it) },
        timeText = state.time,
        onTimeTextChange = { viewModel.onTimeChange(it) },
        onNavigate = { onNavigate() },
        onSaveTodo = { viewModel.insert(it) },
        selectedId = state.selectId,
        stockViewModel = stockViewModel
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DetailScreenComponent(
    todoText: String,
    onTodoTextChange: (String) -> Unit,
    timeText: String,
    onTimeTextChange: (String) -> Unit,
    onNavigate: () -> Unit,
    onSaveTodo: (Todo) -> Unit,
    selectedId: Long,
    stockViewModel: StockViewModel
) {
    val context = LocalContext.current
    val bottomSheetModalState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val coroutineScope = rememberCoroutineScope()

    var apiClient = ObjectDetectAPIClient()

    // The file that saves the photo chosen from the gallery, overwritten each time picking a new one
    val galleryFile = File("${context.filesDir}/food1.png")

    var galleryUri by remember {
        mutableStateOf<Uri?>(
            FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                galleryFile
            )
        )
    }

    // The file that saves the photo taken by camera, overwritten each time taking a new one
    val cameraFile = File("${context.filesDir}/food2.png")

    var cameraUri by remember {
        mutableStateOf<Uri>(
            FileProvider.getUriForFile(
                context,
                context.applicationContext.packageName + ".provider",
                cameraFile
            )
        )
    }

    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }

    var isCameraSelected by remember {
        mutableStateOf(false)
    }

    var takenFromCamera by remember {
        mutableStateOf(false)
    }

    var initPickPicture by remember {
        mutableStateOf(false)
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        galleryUri = uri
        takenFromCamera = false
        initPickPicture = true
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.TakePicture()
    ) { result: Boolean ->
        if (result) {
            takenFromCamera = true
            initPickPicture = true
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            if (isCameraSelected) {
                cameraLauncher.launch(cameraUri)
            } else {
                galleryLauncher.launch("image/*")
            }

            coroutineScope.launch {
                bottomSheetModalState.hide()
            }
        } else {
            Toast.makeText(context, "Permission Denied!", Toast.LENGTH_SHORT).show()
        }
    }

    val isTodoEdit = selectedId == -1L

    var apiResultSet: MutableSet<String> = remember {
        mutableSetOf()
    }

    var uploaded by remember {
        mutableStateOf(false)
    }

    var num by remember {
        mutableStateOf(0)
    }

    var init by remember {
        mutableStateOf(0)
    }

    var numLists: MutableList<String> = MutableList(num) { "" }

    ModalBottomSheetLayout(
        sheetContent = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(CookareTheme.colors.primary.copy(0.08f))
            ) {
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
                    Divider(
                        modifier = Modifier
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
                                        cameraLauncher.launch(cameraUri)
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
                    Divider(
                        modifier = Modifier
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
                    Divider(
                        modifier = Modifier
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
                        androidx.compose.material3.OutlinedButton(
                            onClick = { onNavigate() },
                            modifier = Modifier
                                .size(60.dp)
                                .padding(12.dp),
                            shape = CircleShape,
                            border = BorderStroke(1.5.dp, Color.White),
                            contentPadding = PaddingValues(0.dp),
                        ) {
                            androidx.compose.material3.Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "go back",
                                modifier = Modifier
                                    .padding(8.dp)
                                    .size(30.dp),
                                tint = Color.White
                            )
                        }

                        androidx.compose.material3.Text(
                            buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.White,
                                    )
                                ) {
                                    append("Add food")
                                }
                            }
                        )
                    }
                }



                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            coroutineScope.launch {
                                if (!bottomSheetModalState.isVisible) {
                                    bottomSheetModalState.show()
                                } else {
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
                            text = "Pick a picture",
                            modifier = Modifier.padding(8.dp),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }

                    if (initPickPicture) {
                        if (!takenFromCamera) {
                            if (!isCameraSelected) {
                                bitmap = if (Build.VERSION.SDK_INT < 28) {
                                    MediaStore.Images.Media.getBitmap(
                                        context.contentResolver,
                                        galleryUri
                                    )
                                } else {
                                    val source = ImageDecoder.createSource(
                                        context.contentResolver,
                                        galleryUri!!
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

                            /*
                            var queryImage = decodeFile("${context.filesDir}/food1.png")

                            val thread = thread {
                                searchByImage(queryImage, apiClient)
                            }

                            thread.join()

                             */

                        } else if (takenFromCamera) {
                            bitmap = if (Build.VERSION.SDK_INT < 28) {
                                MediaStore.Images.Media.getBitmap(
                                    context.contentResolver,
                                    cameraUri
                                )
                            } else {
                                val source = ImageDecoder.createSource(
                                    context.contentResolver,
                                    cameraUri
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

                            /*
                            var queryImage = decodeFile("${context.filesDir}/food2.png")

                            val thread = thread {
                                searchByImage(queryImage, apiClient)
                            }

                            thread.join()

                             */
                        }
                    }

                    if(apiResultSet.isNotEmpty()){
                        if(init == 0){
                            num = apiResultSet.size
                            numLists = MutableList(num) { "" }
                            init += 1
                        }
                        for(index in 1..num){
                            OutlinedTextField(
                                colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                                value = apiResultSet.elementAt(index-1),
                                onValueChange = { onTodoTextChange(it) },
                                label = { Text(text = "Enter Food name") }
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            OutlinedTextField(
                                colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                                value = numLists[index-1],
                                onValueChange = {
                                    numLists[index-1] = it
                                },
                                label = { Text(text = "Enter Number") }
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }else{
                        OutlinedTextField(
                            colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                            value = todoText,
                            onValueChange = { onTodoTextChange(it) },
                            label = { Text(text = "Enter Food name") }
                        )
                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
                            value = timeText,
                            onValueChange = { onTimeTextChange(it) },
                            label = { Text(text = "Enter Number") }
                        )
                    }

                    if (initPickPicture) {
                        Button(
                            onClick = {
                                var queryImage = if (!takenFromCamera) {
                                    if (Build.VERSION.SDK_INT < 28) {
                                        MediaStore.Images.Media.getBitmap(
                                            context.contentResolver,
                                            galleryUri
                                        )
                                    } else {
                                        val source = ImageDecoder.createSource(
                                            context.contentResolver,
                                            galleryUri!!
                                        )
                                        ImageDecoder.decodeBitmap(source)
                                    }
                                }
                                else {
                                    decodeFile("${context.filesDir}/food2.png")
                                }

                                val thread = thread {
                                    apiResultSet += searchByImage(queryImage, apiClient)
                                }

                                thread.join()
                                uploaded = true
                            },
                            modifier = Modifier
                                .padding(vertical = 40.dp)
                                .height(50.dp)
                                .width(200.dp)
                                .clip(CircleShape)
                                .background(color = green200)
                        ) {
                            Text(text = "Identify")
                        }
                    }

                    Button(
                        onClick = {
                            if(uploaded){
                               val newStockMap = (1..num).map {
                                   apiResultSet.elementAt(it-1).lowercase() to
                                   (if(numLists[it-1] == "" || !isNumber(numLists[it-1])) 0 else Integer.parseInt(numLists[it-1]))
                               }.toMap()
                                stockViewModel.addStock(newStockMap)
                            }else{
                                val todo = if (isTodoEdit) Todo(todoText, timeText)
                                else Todo(todoText, timeText, id = selectedId)
                                onSaveTodo(todo)
                                stockViewModel.addStock(mapOf(todoText.lowercase() to Integer.parseInt(timeText)))
                            }
                            onNavigate()
                        },
                        modifier = Modifier

                            .padding(vertical = 40.dp)
                            .height(50.dp)
                            .width(200.dp)
                            .clip(CircleShape)

                            .background(color = green200)
                    ) {
                        val text = if (isTodoEdit) "Save" else "Update"
                        Text(text = text)
                    }
                }
            }
        }
    }
}


fun searchByImage(queryImage: Bitmap, apiClient: ObjectDetectAPIClient): Set<String> {
    // Log.i("Detail", "queryImage: $queryImage")
    val objectName = apiClient.annotateImage(queryImage)
    Log.i("Detail", "objectName: $objectName")
    return objectName
}