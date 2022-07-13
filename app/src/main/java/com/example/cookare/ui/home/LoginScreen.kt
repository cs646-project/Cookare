package com.example.cookare.ui.home

import android.annotation.SuppressLint
import androidx.compose.animation.Crossfade
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.guru.fontawesomecomposelib.FaIcon
import com.example.cookare.ui.theme.TextFieldDefaultsMaterial
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.example.cookare.ui.HomeScreenNavigate
import com.example.cookare.R
import com.example.cookare.ui.theme.CookareTheme


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    Scaffold() {
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
                Image(painterResource(R.drawable.logo), "logo",
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

            item {
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
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
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
                    colors = TextFieldDefaultsMaterial.outlinedTextFieldColors(),
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

                Button(
                    onClick = {
                        if (invalidInput(email.text, password.text)) {
                            hasError = true
                            loading_login = false
                        } else {
                            loading_login = true
                            hasError = false
                            onLoginSuccess.invoke()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ) {
                    if (loading_login) {
                        HorizontalDottedProgressBar()
                    } else {
                        Text(text = "Log In")
                    }
                }

                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 2.dp)
                        .height(50.dp)
                        .clip(CircleShape)
                ){
                    if (loading_signup) {
                        HorizontalDottedProgressBar()
                    } else {
                        Text(text = "Sign Up")
                    }
                }
            }

        }
    }
}

fun invalidInput(email: String, password: String) =
    email.isBlank() || password.isBlank()

@Composable
fun HorizontalDottedProgressBar() {
    val color = CookareTheme.colors.onPrimary
    val transition = rememberInfiniteTransition()
    val state by transition.animateFloat(
        initialValue = 0f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 700,
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

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedCrossfadeTargetStateParameter")
@Composable
fun LoginOnboarding() {
//    var loggedIn by remember { mutableStateOf(false) }
    val userStateVM = UserState.current
    val coroutineScope = rememberCoroutineScope()
    Crossfade(targetState = userStateVM.isLoggedIn) {
        if (userStateVM.isLoggedIn) {
            // Text(text = "in LoginOnboaring function")
            HomeScreenNavigate()
        } else {
            LoginScreen {
                coroutineScope.launch {
                    delay(2000)
                    userStateVM.isLoggedIn = true
                }
            }
        }
    }
}