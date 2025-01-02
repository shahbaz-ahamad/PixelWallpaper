package com.shahbaz.pixelwallpaper

import android.widget.Toast
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shahbaz.pixelwallpaper.routs.Routs
import com.shahbaz.pixelwallpaper.screen.category.Category
import com.shahbaz.pixelwallpaper.screen.component.BottomNavigationBar
import com.shahbaz.pixelwallpaper.screen.component.ManageBarVisibility
import com.shahbaz.pixelwallpaper.screen.component.Topbar
import com.shahbaz.pixelwallpaper.screen.favourite.Favourite
import com.shahbaz.pixelwallpaper.screen.homescreen.HomeScreen
import com.shahbaz.pixelwallpaper.screen.setting.Setting
import com.shahbaz.pixelwallpaper.screen.splashscreen.SplashScreen
import kotlin.system.exitProcess

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PixelWallpaperApp(modifier: Modifier = Modifier) {

    val context = LocalContext.current
    val navController = rememberNavController()
    var canShowTopBar by rememberSaveable { mutableStateOf(false) }
    var canShowBottomBar by rememberSaveable { mutableStateOf(false) }

    val stackEntry by navController.currentBackStackEntryAsState()

    ManageBarVisibility(
        currentEntry = { stackEntry },
        showTopBar = { canShowTopBar = it },
        showBottomBar = { canShowBottomBar = it }
    )



    Scaffold(

        topBar = {
            if (canShowTopBar) {
                val title =
                    stackEntry?.destination?.route?.substringAfterLast(".") ?: "Pixel Wallpaper"
                Topbar(
                    title = title,
                )
            }
        },

        bottomBar = {
            if (canShowBottomBar) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { paddingValue ->
        SharedTransitionLayout {
            NavHost(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                navController = navController,
                startDestination = Routs.Splash
            ) {

                composable<Routs.Splash> {
                    SplashScreen(
                        onNavigateToHome = {
                            navController.navigate(Routs.Home)
                        }
                    )
                }

                composable<Routs.Home> {
                    HomeScreen(
                        onBackPress = {
                            exitProcess(0)
                        }
                    )
                }

                composable<Routs.Category> {
                    Category(
                        onBackPress = {
                            navController.navigate(Routs.Home)
                        },
                        onCategoryClick = { categoryName ->
                            Toast.makeText(context,categoryName,Toast.LENGTH_SHORT).show()
                        }
                    )
                }

                composable<Routs.Favourite> {
                    Favourite(
                        onBackPress = {
                            navController.navigate(Routs.Home)
                        }
                    )
                }

                composable<Routs.Setting> {
                    Setting(
                        onBackPress = {
                            navController.navigate(Routs.Home)
                        }
                    )
                }
            }
        }
    }


}