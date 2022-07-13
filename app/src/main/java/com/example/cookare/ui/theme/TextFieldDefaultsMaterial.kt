package com.example.cookare.ui.theme

import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object TextFieldDefaultsMaterial {
    @Composable
    fun textFieldColors(
        textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),
        disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
        backgroundColor: Color = CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.BackgroundOpacity),
        cursorColor: Color = CookareTheme.colors.primary,
        errorCursorColor: Color = CookareTheme.colors.error,
        focusedIndicatorColor: Color =
            CookareTheme.colors.primary.copy(alpha = ContentAlpha.high),
        unfocusedIndicatorColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.UnfocusedIndicatorLineOpacity),
        disabledIndicatorColor: Color = unfocusedIndicatorColor.copy(alpha = ContentAlpha.disabled),
        errorIndicatorColor: Color = CookareTheme.colors.error,
        leadingIconColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = leadingIconColor,
        trailingIconColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = CookareTheme.colors.error,
        focusedLabelColor: Color =
            CookareTheme.colors.primary.copy(alpha = ContentAlpha.high),
        unfocusedLabelColor: Color =CookareTheme.colors.onSurface.copy(ContentAlpha.medium),
        disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
        errorLabelColor: Color = CookareTheme.colors.error,
        placeholderColor: Color = CookareTheme.colors.onSurface.copy(ContentAlpha.medium),
        disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled)
    ): TextFieldColors = androidx.compose.material.TextFieldDefaults.textFieldColors(textColor, disabledTextColor, backgroundColor, cursorColor, errorCursorColor, focusedIndicatorColor, unfocusedIndicatorColor, disabledIndicatorColor, errorIndicatorColor, leadingIconColor, disabledLeadingIconColor, errorLeadingIconColor, trailingIconColor, disabledTrailingIconColor, errorTrailingIconColor, focusedLabelColor, unfocusedLabelColor, disabledLabelColor, errorLabelColor, placeholderColor, disabledPlaceholderColor)

    /**
     * Creates a [TextFieldColors] that represents the default input text, background and content
     * (including label, placeholder, leading and trailing icons) colors used in an
     * [OutlinedTextField].
     */
    @Composable
    fun outlinedTextFieldColors(
        textColor: Color = LocalContentColor.current.copy(LocalContentAlpha.current),
        disabledTextColor: Color = textColor.copy(ContentAlpha.disabled),
        backgroundColor: Color = Color.Transparent,
        cursorColor: Color = CookareTheme.colors.primary,
        errorCursorColor: Color = CookareTheme.colors.error,
        focusedBorderColor: Color =
            CookareTheme.colors.primary.copy(alpha = ContentAlpha.high),
        unfocusedBorderColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = ContentAlpha.disabled),
        disabledBorderColor: Color = unfocusedBorderColor.copy(alpha = ContentAlpha.disabled),
        errorBorderColor: Color = CookareTheme.colors.error,
        leadingIconColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledLeadingIconColor: Color = leadingIconColor.copy(alpha = ContentAlpha.disabled),
        errorLeadingIconColor: Color = leadingIconColor,
        trailingIconColor: Color =
            CookareTheme.colors.onSurface.copy(alpha = TextFieldDefaults.IconOpacity),
        disabledTrailingIconColor: Color = trailingIconColor.copy(alpha = ContentAlpha.disabled),
        errorTrailingIconColor: Color = CookareTheme.colors.error,
        focusedLabelColor: Color =
            CookareTheme.colors.primary.copy(alpha = ContentAlpha.high),
        unfocusedLabelColor: Color = CookareTheme.colors.onSurface.copy(ContentAlpha.medium),
        disabledLabelColor: Color = unfocusedLabelColor.copy(ContentAlpha.disabled),
        errorLabelColor: Color = CookareTheme.colors.error,
        placeholderColor: Color = CookareTheme.colors.onSurface.copy(ContentAlpha.medium),
        disabledPlaceholderColor: Color = placeholderColor.copy(ContentAlpha.disabled)
    ): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(textColor, disabledTextColor, backgroundColor, cursorColor, errorCursorColor, focusedBorderColor, unfocusedBorderColor, disabledBorderColor, errorBorderColor, leadingIconColor, disabledLeadingIconColor, errorLeadingIconColor, trailingIconColor, disabledTrailingIconColor, errorTrailingIconColor, focusedLabelColor, unfocusedLabelColor, disabledLabelColor, errorLabelColor, placeholderColor, disabledPlaceholderColor)
}