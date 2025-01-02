package com.shahbaz.pixelwallpaper.screen.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shahbaz.pixelwallpaper.data.repositiory.PixelWallpaperRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class WallpaperViewmodel @Inject constructor(
    private val repo: PixelWallpaperRepo
) : ViewModel() {

    val getWallpaper = repo.getPixelWallpaper().flow.cachedIn(viewModelScope)
}