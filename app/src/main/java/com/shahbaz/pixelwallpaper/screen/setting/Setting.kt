package com.shahbaz.pixelwallpaper.screen.setting

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable

@Composable
fun Setting(onBackPress: () -> Unit) {

    BackHandler { onBackPress() }
}