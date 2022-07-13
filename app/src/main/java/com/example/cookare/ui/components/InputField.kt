package com.example.cookare.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cookare.ui.theme.Gray
import com.example.cookare.ui.theme.green100


@Composable
fun InputField (cata:String,input_item:String ){
    var pwd by remember { mutableStateOf(TextFieldValue("")) }

    Row(
        Modifier
            .fillMaxWidth()
            ,
        verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = cata,
            style = MaterialTheme.typography.subtitle2,
            fontSize = 16.sp,
            color = Gray,
            modifier = Modifier.size(100.dp,20.dp).padding(horizontal = 10.dp)


        )
        TextField(value = pwd,
            placeholder = { Text(text = input_item) },
            onValueChange = { str -> pwd = str },
            modifier = Modifier.scale(scaleY = 0.8F, scaleX = 0.8F),
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = green100)

        )


    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview1() {

    InputField(cata = "User Name", input_item = "123" )


}