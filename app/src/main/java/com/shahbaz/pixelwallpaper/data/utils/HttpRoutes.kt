package com.shahbaz.pixelwallpaper.data.utils

object HttpRoutes {
    private const val BASE_URL = "https://api.pexels.com/v1"
    const val WALLPAPER = "$BASE_URL/curated"
    const val SEARCH_WALLPAPERS = "${BASE_URL}/search"
}