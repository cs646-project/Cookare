package com.example.cookare.ui.theme

import com.example.cookare.ui.theme.ColorPallet

data class AppThemeState(
    var darkTheme: Boolean = false,
    var pallet: ColorPallet = ColorPallet.GREEN
)