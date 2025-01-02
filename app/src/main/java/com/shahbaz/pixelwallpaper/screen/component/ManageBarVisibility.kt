package com.shahbaz.pixelwallpaper.screen.component

import android.view.WindowInsets
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import com.shahbaz.pixelwallpaper.routs.Routs

@Composable
fun ManageBarVisibility(
    currentEntry: () -> NavBackStackEntry?,
    showTopBar: (Boolean) -> Unit
) {

    currentEntry()?.let { entry ->
        val route = entry.destination.route?.substringBefore("/")

        when (route) {

            in arrayOf(
                Routs.Splash::class.qualifiedName
            ) -> {
                showTopBar(false)
            }

            else -> {
                showTopBar(true)
            }
        }


        if (route in arrayOf(Routs.Splash::class.qualifiedName)) {
            HideSystemBar()
        }
    }
}