package com.shahbaz.pixelwallpaper.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WallpaperResponse(
    @SerialName("next_page") val nextPage: String? = null,
    @SerialName("page") val page: Int,
    @SerialName("per_page") val perPage: Int,
    @SerialName("photos") val photos: List<Photo>,
    @SerialName("total_results") val totalResults: Int,
    @SerialName("prev_page") val prevPage: String? = null,
)
