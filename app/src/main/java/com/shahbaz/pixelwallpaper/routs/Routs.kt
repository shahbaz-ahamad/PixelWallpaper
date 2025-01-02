package com.shahbaz.pixelwallpaper.routs

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routs {

    @Serializable
    data object Splash : Routs

    @Serializable
    data object Home : Routs
}