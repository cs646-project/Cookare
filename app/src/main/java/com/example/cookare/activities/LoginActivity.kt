package com.example.cookare.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.example.cookare.MainActivity
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme
import com.example.cookare.ui.theme.TextFieldDefaultsMaterial
import com.example.cookare.ui.theme.green500
import com.google.gson.GsonBuilder
import com.guru.fontawesomecomposelib.FaIcon
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import kotlin.concurrent.thread

class LoginActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CookareTheme {
                LoginSreen()
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
fun LoginSreen(){
    Scaffold(){
        var email by remember { mutableStateOf(TextFieldValue("")) }
        var password by remember { mutableStateOf(TextFieldValue("")) }
        var hasError by remember { mutableStateOf(false) }
        var passwordVisualTransformation by remember {
            mutableStateOf<VisualTransformation>(
                PasswordVisualTransformation()
            )
        }

        val passwordInteractionState = remember { MutableInteractionSource() }
        val emailInteractionState = remember { MutableInteractionSource() }

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
                    modifier = Modifier.padding(top = 10.dp)
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
                var loading_login by remember { mutableStateOf(false) }
                var loading_signup by remember { mutableStateOf(false) }
                var is_match by remember { mutableStateOf(false) }

                Button(
                    onClick = {
                        val thread = thread {
                            is_match = matchInput(email.text, password.text)
                        }

                        thread.join()

                        if((!invalidInput(email.text, password.text)) && is_match){
                            loading_login = true
                            hasError = false
                        }else{
                            loading_login = false
                            hasError = true
                        }
                    },
                    colors = ButtonDefaults.buttonColors(green500),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ){
                    if (loading_login) {
                        HorizontalDottedProgressBar()
                        val context = LocalContext.current
                        context.startActivity(Intent(context, MainActivity::class.java))
                        // loading_login = false
                    } else {
                        Text(text = "Log In")
                    }
                }

                if(hasError){
                    AlertDialog(
                        onDismissRequest = {
                            hasError = false
                        },
                        title = {
                            Text(text = "Error")
                        },
                        text = {
                            Text("Please input correct email and password")
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    hasError = false
                                }) {
                                Text("OK")
                            }
                        }
                    )
                }

                Button(
                    onClick = {
                        loading_signup = true
                    },
                    colors = ButtonDefaults.buttonColors(green500),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ){
                    if (loading_signup) {
                        HorizontalDottedProgressBar()
                        val context = LocalContext.current
                        context.startActivity(Intent(context, SignupActivity::class.java))
                        // loading_login = false
                    } else {
                        Text(text = "Sign Up")
                    }
                }

            }
        }

    }
}

@Composable
fun HorizontalDottedProgressBar() {
    val color = androidx.compose.material.MaterialTheme.colors.onPrimary
    val transition = rememberInfiniteTransition()
    val state by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 800,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    DrawCanvas(state = state, color = color)
}

@Composable
fun DrawCanvas(
    state: Float,
    color: Color,
) {
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
    ) {

        val radius = (4.dp).value
        val padding = (6.dp).value

        for (i in 1..5) {
            if (i - 1 == state.toInt()) {
                drawCircle(
                    radius = radius * 2,
                    brush = SolidColor(color),
                    center = Offset(
                        x = this.center.x + radius * 2 * (i - 3) + padding * (i - 3),
                        y = this.center.y
                    )
                )
            } else {
                drawCircle(
                    radius = radius,
                    brush = SolidColor(color),
                    center = Offset(
                        x = this.center.x + radius * 2 * (i - 3) + padding * (i - 3),
                        y = this.center.y
                    )
                )
            }
        }
    }
}


fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()


fun matchInput(email: String, password: String):Boolean{
    val jsonObject = JSONObject()
    jsonObject.put("method", 0)
    jsonObject.put("loginCard", email)
    jsonObject.put("password", password)
    val payload = jsonObject.toString()

    val okHttpClient = OkHttpClient()
    val requestBody = payload.toRequestBody()
    val request = Request.Builder()
        .url("http://101.43.180.143:9090/login/login")
        .addHeader("Content-Type","application/json")
        .addHeader("Accept", "*/*")
        .addHeader("Accept-Encoding", "gzip, deflate, br")
        .addHeader("Connection", "keep-alive")
        .post(requestBody)
        .build()

    val response = OkHttpClient().newCall(request).execute()
    // println("response: " + response)
    val body = response?.body?.string()
    // println("body: " + body)
    val gson = GsonBuilder().create()
    val login = gson.fromJson(body, Login::class.java)

    // println("login.code" + login.code)

    if(login.code == 1) return true
    else return false


    /**
    okHttpClient.newCall(request).enqueue(object : Callback{
    override fun onFailure(call: Call, e: IOException) {
    e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
    val body = response?.body?.string()
    val gson = GsonBuilder().create()
    val login = gson.fromJson(body, Login::class.java)
    if(login.code == 1){
    val ret = true
    }
    }
    })**/
}

class Login(val code: Int, val msg: String, val data: Data)

class Data(val token: String, val user: User)

class User(val id: Int, val username: String, val password: String, val email: String, val phone: String,
           val avatarUrl: String, val createTime: String, val updateTime: String, val deleteFlg: Int,
           val tags: String)