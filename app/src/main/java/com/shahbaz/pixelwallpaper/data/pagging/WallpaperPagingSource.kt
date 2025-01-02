package com.shahbaz.pixelwallpaper.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahbaz.pixelwallpaper.data.remote.PixelWallpaperApi
import com.shahbaz.pixelwallpaper.model.Photo

class WallpaperPagingSource(
    private val api: PixelWallpaperApi
) : PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        try {
            val page = params.key ?: 1
            val response = api.getWallpaper(page)

            return LoadResult.Page(
                data = response.photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.photos.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

}