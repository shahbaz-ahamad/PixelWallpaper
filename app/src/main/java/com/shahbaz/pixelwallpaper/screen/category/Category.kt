package com.shahbaz.pixelwallpaper.screen.category

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Category(onBackPress: () -> Unit) {
    BackHandler { onBackPress() }
}