package com.example.cookare.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cookare.MainActivity
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.TextFieldDefaultsMaterial
import com.example.cookare.ui.theme.green500
import com.guru.fontawesomecomposelib.FaIcon

class SignupActivity : ComponentActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookareTheme {
                SignupSreen()
            }
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun SignupSreen(){
    Scaffold(){
        var username by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var re_password by remember { mutableStateOf(TextFieldValue("")) }
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var hasError by remember { mutableStateOf(false) }

        val usernameInteractionState = remember { MutableInteractionSource() }
        val passwordInteractionState = remember { MutableInteractionSource() }
        val repasswordInteractionState = remember { MutableInteractionSource() }
        val emailInteractionState = remember { MutableInteractionSource() }

        var passwordVisualTransformation by remember {
            mutableStateOf<VisualTransformation>(
                PasswordVisualTransformation()
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            item { Spacer(modifier = Modifier.height(20.dp)) }
            item {
                Text(
                    text = "Cookare",
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.ExtraBold),
                    modifier = Modifier.padding(top = 20.dp)
                )
                Image(
                    painterResource(R.drawable.logo), "logo",
                    Modifier
                        .clip(CircleShape)
                        .fillMaxWidth())
            }
            item {
                Text(
                    text = "Cares you most",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 100.dp)
                )
            }
            item{
                androidx.compose.material.OutlinedTextField(
                    value = username,
                    leadingIcon = {
                        FaIcon(
                            faIcon = FaIcons.Bookmark,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        )
                    },
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(
                        focusedBorderColor = green500,
                        cursorColor = Color.Black
                    ),
                    label = { Text(text = "User Name") },
                    placeholder = { Text(text = "") },
                    onValueChange = {
                        username = it
                    },
                    interactionSource = usernameInteractionState,
                )
            }
            item{
                androidx.compose.material.OutlinedTextField(
                    value = email,
                    leadingIcon = {
                        FaIcon(
                            faIcon = FaIcons.Envelope,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        )
                    },
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(
                        focusedBorderColor = green500,
                        cursorColor = Color.Black
                    ),
                    label = { Text(text = "Email address") },
                    placeholder = { Text(text = "") },
                    onValueChange = {
                        email = it
                    },
                    interactionSource = emailInteractionState,
                )
            }
            item {
                androidx.compose.material.OutlinedTextField(
                    value = password,
                    leadingIcon = {
                        FaIcon(
                            faIcon = FaIcons.Key,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        )
                    },
                    trailingIcon = {
                        FaIcon(
                            faIcon = FaIcons.EyeSlash,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                            modifier = Modifier.clickable(onClick = {
                                passwordVisualTransformation =
                                    if (passwordVisualTransformation != VisualTransformation.None) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    }
                            })
                        )
                    },
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(
                        focusedBorderColor = green500,
                        cursorColor = Color.Black
                    ),
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Password") },
                    placeholder = { Text(text = "") },
                    onValueChange = {
                        password = it
                    },
                    interactionSource = passwordInteractionState,
                    visualTransformation = passwordVisualTransformation,
                )
            }
            item {
                androidx.compose.material.OutlinedTextField(
                    value = re_password,
                    leadingIcon = {
                        FaIcon(
                            faIcon = FaIcons.Key,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
                        )
                    },
                    trailingIcon = {
                        FaIcon(
                            faIcon = FaIcons.EyeSlash,
                            tint = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
                            modifier = Modifier.clickable(onClick = {
                                passwordVisualTransformation =
                                    if (passwordVisualTransformation != VisualTransformation.None) {
                                        VisualTransformation.None
                                    } else {
                                        PasswordVisualTransformation()
                                    }
                            })
                        )
                    },
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(
                        focusedBorderColor = green500,
                        cursorColor = Color.Black
                    ),
                    maxLines = 1,
                    isError = hasError,
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    label = { Text(text = "Re-entered password") },
                    placeholder = { Text(text = "") },
                    onValueChange = {
                        re_password = it
                    },
                    interactionSource = repasswordInteractionState,
                    visualTransformation = passwordVisualTransformation,
                )
            }
            item {
                var loading_signup by remember { mutableStateOf(false) }
                var loading_back by remember { mutableStateOf(false) }
                // 0: none; 1: empty; 2: mismatch
                var hasError by remember { mutableStateOf(0) }
                // var is_match by remember { mutableStateOf(false) }
                // var is_empty by remember { mutableStateOf(false) }

                Button(
                    onClick = {


                        println("password.text == re_password.text" + password.text == re_password.text)

                        if(matchPassword(password.text, re_password.text)){
                            hasError = 2
                        }

                        if(isEmpty(username.text, email.text, password.text, re_password.text)){
                            hasError = 1
                        }

                    },
                    colors = ButtonDefaults.buttonColors(green500),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ){
                    if(loading_signup){
                        HorizontalDottedProgressBar()
                        val context = LocalContext.current
                        context.startActivity(Intent(context, MainActivity::class.java))
                        // loading_signup = false
                    }else{
                        Text(text = "Sign Up")
                        println("hasError: " + hasError)
                        if(hasError == 1){
                            AlertDialog(
                                onDismissRequest = {
                                    hasError = 0
                                },
                                title = {
                                    Text(text = "Error")
                                },
                                text = {
                                    Text("Please fill out all the black fields.")
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            hasError = 0
                                        }) {
                                        Text("OK")
                                    }
                                }
                            )
                        }

                        if(hasError == 2){
                            AlertDialog(
                                onDismissRequest = {
                                    hasError = 0
                                },
                                title = {
                                    Text(text = "Error")
                                },
                                text = {
                                    Text("Please check your re-entered password.")
                                },
                                confirmButton = {
                                    Button(
                                        onClick = {
                                            hasError = 0
                                        }) {
                                        Text("OK")
                                    }
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        loading_back = true
                    },
                    colors = ButtonDefaults.buttonColors(green500),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ){
                    if(loading_back){
                        HorizontalDottedProgressBar()
                        val context = LocalContext.current
                        context.startActivity(Intent(context, LoginActivity::class.java))
                        // loading_back = false
                    }else{
                        Text(text = "Back")
                    }
                }
            }
        }
    }
}

fun isEmpty(username: String, email: String, password: String, re_password: String) =
    username.isBlank() || email.isBlank() || password.isBlank() || re_password.isBlank()

fun matchPassword(password: String, re_password: String) =
    password != re_password





@OptIn(ExperimentalAnimationApi::class,
    ExperimentalFoundationApi::class,
    ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SignupSreen()
}