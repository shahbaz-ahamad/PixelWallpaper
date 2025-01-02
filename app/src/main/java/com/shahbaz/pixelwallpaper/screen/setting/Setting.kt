package com.shahbaz.pixelwallpaper.screen.setting

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Setting(onBackPress: () -> Unit) {

    BackHandler { onBackPress() }
}