package com.shahbaz.pixelwallpaper.data.pagging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahbaz.pixelwallpaper.data.remote.PixelWallpaperApi
import com.shahbaz.pixelwallpaper.model.Photo

class SearchWallpaperPagingSource(
    private val api: PixelWallpaperApi,
    private val query: String
) : PagingSource<Int, Photo>() {
    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val page = params.key ?: 1
        try {
            val result = api.searchWallpaper(query, page)
            return LoadResult.Page(
                data = result.photos,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (result.photos.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}