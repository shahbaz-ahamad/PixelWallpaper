package com.shahbaz.pixelwallpaper.screen.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.shahbaz.pixelwallpaper.routs.Routs

@Composable
fun ManageBarVisibility(
    currentEntry: () -> NavBackStackEntry?,
    showTopBar: (Boolean) -> Unit,
    showBottomBar: (Boolean) -> Unit
) {

    currentEntry()?.let { entry ->
        val route = entry.destination.route?.substringBefore("/")

        when (route) {

            in arrayOf(
                Routs.Splash::class.qualifiedName,
                Routs.Search::class.qualifiedName
            ) -> {
                showTopBar(false)
                showBottomBar(false)
            }

            else -> {
                showTopBar(true)
                showBottomBar(true)
            }
        }


        if (route in arrayOf(Routs.Splash::class.qualifiedName)) {
            HideSystemBar()
        }
    }
}