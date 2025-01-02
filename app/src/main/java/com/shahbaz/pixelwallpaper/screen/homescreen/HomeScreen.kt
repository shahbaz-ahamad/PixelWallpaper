package com.shahbaz.pixelwallpaper.screen.homescreen

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahbaz.pixelwallpaper.model.Photo
import com.shahbaz.pixelwallpaper.screen.component.LoadingPlaceHolder
import com.shahbaz.pixelwallpaper.screen.component.WallpaperItem

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit,
    wallpaper: LazyPagingItems<Photo>
) {


    BackHandler { onBackPress() }


    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        contentPadding = PaddingValues(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .fillMaxSize()

    ) {

        if (wallpaper.loadState.refresh == LoadState.Loading) {
            items(20) {
                LoadingPlaceHolder()
            }
        }

        items(wallpaper.itemCount, key = { "${wallpaper[it]?.id}_$it" }) { index ->
            val wallpaperItem = wallpaper[index]
            if (wallpaperItem != null) {
                WallpaperItem(
                    imageUrl = wallpaperItem.src.portrait
                )
            }
        }
    }

}