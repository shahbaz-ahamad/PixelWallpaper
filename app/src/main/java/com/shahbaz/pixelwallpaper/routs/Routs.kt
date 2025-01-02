package com.shahbaz.pixelwallpaper.routs

import kotlinx.serialization.Serializable

@Serializable
sealed interface Routs {

    @Serializable
    data object SplashScreen : Routs

    @Serializable
    data object HomeScreen : Routs
}