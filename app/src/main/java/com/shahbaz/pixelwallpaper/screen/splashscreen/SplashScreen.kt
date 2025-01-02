package com.shahbaz.pixelwallpaper.screen.splashscreen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shahbaz.pixelwallpaper.R
import com.shahbaz.pixelwallpaper.screen.component.SplashProgressBar


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashScreenViewmodel: SplashScreenViewmodel = hiltViewModel(),
    onNavigateToHome: () -> Unit
) {

    val progress by splashScreenViewmodel.progress.collectAsStateWithLifecycle()

    //disable the back click on the splashscreen
    BackHandler(enabled = false) { }

    val splashScreenTitle = remember {
        buildAnnotatedString {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 29.sp))
            append("Shahbaz's")
            pushStyle(SpanStyle(fontWeight = FontWeight.Light, fontSize = 24.sp))
            append(" Wallpaper")
        }
    }


    //navigate to the home screen when the progress is 1f
    LaunchedEffect(key1 = progress) {
        if (progress >= 1f) {
            onNavigateToHome()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = stringResource(R.string.app_name),
            modifier = Modifier
                .size(100.dp),
            contentScale = ContentScale.Fit
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = splashScreenTitle, color = MaterialTheme.colorScheme.onBackground)
        Spacer(modifier = Modifier.height(20.dp))
        SplashProgressBar(progress = progress)
    }
}