package com.shahbaz.pixelwallpaper

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shahbaz.pixelwallpaper.routs.Routs
import com.shahbaz.pixelwallpaper.screen.homescreen.HomeScreen
import com.shahbaz.pixelwallpaper.screen.splashscreen.SplashScreen
import kotlin.system.exitProcess

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun PixelWallpaperApp(modifier: Modifier = Modifier) {

    val navController = rememberNavController()

    Scaffold { paddingValue ->
        SharedTransitionLayout {
            NavHost(
                modifier = modifier
                    .fillMaxSize()
                    .padding(paddingValue),
                navController = navController,
                startDestination = Routs.SplashScreen
            ) {

                composable<Routs.SplashScreen> {
                    SplashScreen(
                        onNavigateToHome = {
                            navController.navigate(Routs.HomeScreen)
                        }
                    )
                }

                composable<Routs.HomeScreen> {
                    HomeScreen(
                        onBackPress = {
                            exitProcess(0)
                        }
                    )
                }
            }
        }
    }


}