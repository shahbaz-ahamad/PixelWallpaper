package com.shahbaz.pixelwallpaper.screen.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.shahbaz.pixelwallpaper.routs.IconType
import com.shahbaz.pixelwallpaper.routs.bottomNavigationItems

@Composable
fun BottomNavigationBar(
    navController: NavHostController
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        bottomNavigationItems.forEach { bottomNavItem ->
            val isSelected =
                currentDestination?.hierarchy?.any { it.route == bottomNavItem.routs::class.qualifiedName } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(bottomNavItem.routs) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    when (bottomNavItem.icon) {
                        is IconType.Drawable -> {
                            Icon(
                                painter = painterResource(
                                    id = if (isSelected && bottomNavItem.selectedIcon is IconType.Drawable)
                                        bottomNavItem.selectedIcon.iconRes
                                    else bottomNavItem.icon.iconRes
                                ),
                                contentDescription = bottomNavItem.title,
                            )
                        }

                        is IconType.Vector -> {
                            Icon(
                                imageVector = if (isSelected && bottomNavItem.selectedIcon is IconType.Vector)
                                    bottomNavItem.selectedIcon.icon
                                else bottomNavItem.icon.icon,
                                contentDescription = bottomNavItem.title,
                            )
                        }
                    }
                },
                alwaysShowLabel = false,
            )
        }
    }
}