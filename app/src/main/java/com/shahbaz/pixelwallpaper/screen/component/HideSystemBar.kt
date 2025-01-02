package com.shahbaz.pixelwallpaper.screen.component

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat

@Composable
fun HideSystemBar() {
    val view = LocalView.current
    val window = (view.context as Activity).window
    val insetsController = WindowCompat.getInsetsController(window,view)
    insetsController.hide(WindowInsetsCompat.Type.systemBars())
}