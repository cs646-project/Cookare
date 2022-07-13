package com.example.cookare.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.cookare.ui.theme.ColorPallet.*

// dark palettes
private val DarkGreenColorPalette = darkColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White,
    error = Color.Red,
)

private val DarkPurpleColorPalette = darkColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White,
    error = Color.Red,
)

private val DarkBlueColorPalette = darkColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White,
    error = Color.Red,
)

private val DarkOrangeColorPalette = darkColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.Black,
    background = Color.Black,
    surface = Color.Black,
    surfaceVariant = Color.Black,
    onPrimary = Color.Black,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color.White,
    error = Color.Red,
)

// Light pallets
private val LightGreenColorPalette = lightColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.White,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black
)

private val LightPurpleColorPalette = lightColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.White,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black
)

private val LightBlueColorPalette = lightColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.White,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black
)

private val LightOrangeColorPalette = lightColorScheme(
    primary = green500,
    primaryContainer = green200,
    onPrimaryContainer = green700,
    secondary = teal200,
    secondaryContainer = green700,
    onSecondaryContainer = Color.White,
    background = Color.White,
    surface = Color.White,
    surfaceVariant = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onSurfaceVariant = Color.Black
)


@Composable
fun ComposeCookBookMaterial3Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorPallet: ColorPallet = WALLPAPER,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current

    val colors = when (colorPallet) {
        GREEN -> if (darkTheme) DarkGreenColorPalette else LightGreenColorPalette
        PURPLE -> if (darkTheme) DarkPurpleColorPalette else LightPurpleColorPalette
        ORANGE -> if (darkTheme) DarkOrangeColorPalette else LightOrangeColorPalette
        BLUE -> if (darkTheme) DarkBlueColorPalette else LightBlueColorPalette
        WALLPAPER -> if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            if (darkTheme)
                dynamicDarkColorScheme(context)
            else
                dynamicLightColorScheme(context)
        else
            if (darkTheme)
                DarkGreenColorPalette
            else LightGreenColorPalette
    }
    androidx.compose.material3.MaterialTheme(
        colorScheme = colors,
        content = content
    )
}