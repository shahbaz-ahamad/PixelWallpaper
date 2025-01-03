package com.shahbaz.pixelwallpaper.data.remote

import com.shahbaz.pixelwallpaper.model.WallpaperResponse

interface PixelWallpaperApi {
    suspend fun getWallpaper(page: Int): WallpaperResponse
    suspend fun searchWallpaper(query: String, page: Int): WallpaperResponse
}