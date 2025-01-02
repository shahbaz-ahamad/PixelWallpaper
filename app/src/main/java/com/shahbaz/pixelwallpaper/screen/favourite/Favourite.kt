package com.shahbaz.pixelwallpaper.screen.favourite

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Favourite(onBackPress: () -> Unit) {

    BackHandler { onBackPress() }

}