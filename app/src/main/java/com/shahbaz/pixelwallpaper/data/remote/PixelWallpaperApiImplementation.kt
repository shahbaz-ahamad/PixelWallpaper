package com.shahbaz.pixelwallpaper.data.remote

import com.shahbaz.pixelwallpaper.data.utils.Constant
import com.shahbaz.pixelwallpaper.data.utils.HttpRoutes
import com.shahbaz.pixelwallpaper.model.WallpaperResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class PixelWallpaperApiImplementation(
    private val client: HttpClient
) : PixelWallpaperApi {

    override suspend fun getWallpaper(page: Int): WallpaperResponse {
        val response = client.get(HttpRoutes.WALLPAPER) {
            parameter("page", page)
            parameter("per_page", Constant.PER_PAGE_ITEMS)
        }.body<WallpaperResponse>()
        return response
    }

    override suspend fun searchWallpaper(query: String, page: Int): WallpaperResponse {
        val response = client.get(
            HttpRoutes.SEARCH_WALLPAPERS
        ) {
            parameter("query", query)
            parameter("page", page)
            parameter("per_page", Constant.PER_PAGE_ITEMS)
        }.body<WallpaperResponse>()
        return response
    }
}