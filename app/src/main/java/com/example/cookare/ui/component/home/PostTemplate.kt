package com.example.cookare.ui.component.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.InternalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.ui.component.home.HomeScreen
import com.example.cookare.ui.component.home.HorizontalDottedProgressBar
import com.example.cookare.ui.component.home.invalidInput
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun PostTemplate() {
    LazyColumn(state = rememberLazyListState()) {
        item { TextInputs() }

        item {
            var loading by remember { mutableStateOf(false) }
            Button(
                onClick = {
                    loading = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(50.dp)
                    .clip(CircleShape)
            ) {
                if (loading) {
                    HorizontalDottedProgressBar()
                } else {
                    Text(text = "Add")
                }
            }
        }
    }
    // TextInputs()
}

@Composable
fun TextInputs(){
    Text(text = "Add Your Post", modifier = Modifier.padding(8.dp), fontSize = 35.sp, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),)
    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var tips by remember { mutableStateOf(TextFieldValue("")) }
    var time by remember { mutableStateOf(TextFieldValue("")) }
    var level by remember { mutableStateOf(TextFieldValue("")) }
    var tag by remember { mutableStateOf(TextFieldValue("")) }
    var step_1 by remember { mutableStateOf(TextFieldValue("")) }
    var step_2 by remember { mutableStateOf(TextFieldValue("")) }
    var step_3 by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = title,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Title") },
        placeholder = { Text(text = "") },
        // visualTransformation = PasswordVisualTransformation(),
        onValueChange = {
            title = it
        }
    )

    OutlinedTextField(
        value = description,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Description") },
        placeholder = { Text(text = "") },
        onValueChange = {
            description = it
        }
    )

    OutlinedTextField(
        value = tips,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Tips") },
        placeholder = { Text(text = "") },
        onValueChange = {
            tips = it
        }
    )

    OutlinedTextField(
        value = time,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Time") },
        placeholder = { Text(text = "") },
        onValueChange = {
            time = it
        }
    )

    OutlinedTextField(
        value = level,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Level") },
        placeholder = { Text(text = "") },
        onValueChange = {
            level = it
        }
    )

    OutlinedTextField(
        value = tag,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Tag") },
        placeholder = { Text(text = "") },
        onValueChange = {
            tag = it
        }
    )

    Text(text = "Procedure", modifier = Modifier.padding(8.dp), fontSize = 20.sp, style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),)

    OutlinedTextField(
        value = step_1,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 1") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_1 = it
        }
    )

    OutlinedTextField(
        value = step_2,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 2") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_2 = it
        }
    )

    OutlinedTextField(
        value = step_3,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        label = { Text(text = "Step 3") },
        placeholder = { Text(text = "") },
        onValueChange = {
            step_3 = it
        }
    )
}

@InternalTextApi
@Preview
@Composable
fun PreviewInputs() {
    PostTemplate()
}