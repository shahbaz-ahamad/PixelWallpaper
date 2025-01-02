package com.shahbaz.pixelwallpaper.data.repositiory

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shahbaz.pixelwallpaper.data.pagging.WallpaperPagingSource
import com.shahbaz.pixelwallpaper.data.remote.PixelWallpaperApi
import com.shahbaz.pixelwallpaper.data.utils.Constant

class PixelWallpaperRepo(
    private val api: PixelWallpaperApi
) {
    fun getPixelWallpaper() = Pager(
        config = PagingConfig(pageSize = Constant.PER_PAGE_ITEMS, prefetchDistance = 10),
        pagingSourceFactory = { WallpaperPagingSource(api) }
    )
}