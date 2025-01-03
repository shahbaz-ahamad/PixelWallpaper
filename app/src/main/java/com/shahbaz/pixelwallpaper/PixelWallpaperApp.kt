package com.shahbaz.pixelwallpaper

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahbaz.pixelwallpaper.routs.Routs
import com.shahbaz.pixelwallpaper.screen.category.Category
import com.shahbaz.pixelwallpaper.screen.category.CategoryDetailScreen
import com.shahbaz.pixelwallpaper.screen.component.BottomNavigationBar
import com.shahbaz.pixelwallpaper.screen.component.ManageBarVisibility
import com.shahbaz.pixelwallpaper.screen.component.Topbar
import com.shahbaz.pixelwallpaper.screen.favourite.Favourite
import com.shahbaz.pixelwallpaper.screen.homescreen.HomeScreen
import com.shahbaz.pixelwallpaper.screen.homescreen.WallpaperViewmodel
import com.shahbaz.pixelwallpaper.screen.search.SearchScreen
import com.shahbaz.pixelwallpaper.screen.setting.Setting
import com.shahbaz.pixelwallpaper.screen.splashscreen.SplashScreen
import kotlin.system.exitProcess

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PixelWallpaperApp(modifier: Modifier = Modifier) {

    val wallpaperViewmodel: WallpaperViewmodel = hiltViewModel()
    val wallpaper = wallpaperViewmodel.getWallpaper.collectAsLazyPagingItems()

    val navController = rememberNavController()
    var canShowTopBar by rememberSaveable { mutableStateOf(false) }
    var canShowBottomBar by rememberSaveable { mutableStateOf(false) }


    val stackEntry by navController.currentBackStackEntryAsState()


    var category by remember {
        mutableStateOf("")
    }

    var categoryWiseList = wallpaperViewmodel.searchWallpaper(category).collectAsLazyPagingItems()

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
                    onSearchClick = {
                        navController.navigate(Routs.Search)
                    }
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
                        },
                        wallpaper = wallpaper
                    )
                }

                composable<Routs.Category> {
                    Category(
                        onBackPress = {
                            navController.navigate(Routs.Home)
                        },
                        onCategoryClick = { categoryName ->
                            navController.navigate(Routs.CategoryDetail(categoryName))
                        }
                    )
                }

                composable<Routs.Search> {
                    SearchScreen(
                        onBackArrowPress = {
                            navController.navigateUp()
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

                composable<Routs.CategoryDetail> { backStackEntry ->
                    val categoryDetail: Routs.CategoryDetail = backStackEntry.toRoute()
                    category = categoryDetail.categoryName

                    CategoryDetailScreen(
                        category = category,
                        wallpaperList = categoryWiseList,
                        onBackPress = {
                            navController.navigateUp()
                        }
                    )
                }
            }
        }
    }


}