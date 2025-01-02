package com.shahbaz.pixelwallpaper.routs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.shahbaz.pixelwallpaper.R

sealed interface IconType {
    data class Vector(val icon: ImageVector) : IconType
    data class Drawable(val iconRes: Int) : IconType
}


data class BottomNavRoutes(
    val title: String,
    val routs: Routs,
    val icon: IconType,
    val selectedIcon: IconType
)

val bottomNavigationItems = listOf(
    BottomNavRoutes(
        title = "Home",
        routs = Routs.Home,
        icon = IconType.Vector(Icons.Outlined.Home),
        selectedIcon = IconType.Vector(Icons.Filled.Home)
    ),
    BottomNavRoutes(
        title = "Category",
        routs = Routs.Category,
        icon = IconType.Drawable(R.drawable.category),
        selectedIcon = IconType.Drawable(R.drawable.category_filled)
    ),
    BottomNavRoutes(
        title = "Favourite",
        routs = Routs.Favourite,
        icon = IconType.Vector(Icons.Outlined.FavoriteBorder),
        selectedIcon = IconType.Vector(Icons.Filled.Favorite)
    ),
    BottomNavRoutes(
        title = "Setting",
        routs = Routs.Setting,
        icon = IconType.Vector(Icons.Outlined.Settings),
        selectedIcon = IconType.Vector(Icons.Outlined.Settings)
    )
)