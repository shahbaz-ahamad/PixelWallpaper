package com.shahbaz.pixelwallpaper.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun WallpaperItem(
    modifier: Modifier = Modifier,
    imageUrl: String
) {

    var showShimmer by remember { mutableStateOf(true) }

    AsyncImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        onSuccess = {
            showShimmer = false
        },
        modifier = modifier
            .background(
                shimmerBrush(targetValue = 1300f, showShimmer = showShimmer),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .height(200.dp)
            .fillMaxWidth()
    )
}