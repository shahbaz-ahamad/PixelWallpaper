package com.shahbaz.pixelwallpaper.screen.category

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.compose.LazyPagingItems
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.shahbaz.pixelwallpaper.model.Category
import com.shahbaz.pixelwallpaper.screen.component.LoadingPlaceHolder
import com.shahbaz.pixelwallpaper.screen.component.shimmerBrush
import kotlinx.coroutines.delay

@Composable
fun Category(
    onBackPress: () -> Unit,
    onCategoryClick: (String) -> Unit,
) {
    BackHandler { onBackPress() }

    var showContent by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = Unit) {
        delay(250)
        showContent = true
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (showContent) {
            items(categories, key = { it.name }) { category ->
                CategoryItem(category = category, onClick = {
                    onCategoryClick(category.name)
                })
            }
        } else {
            items(categories.size) {
                LoadingPlaceHolder(modifier = Modifier.height(100.dp))
            }
        }
    }
}


@Composable
fun CategoryItem(
    category: Category,
    onClick: () -> Unit
) {

    val context = LocalContext.current
    val categoryImageSize = Size(800.dp.toPx().toInt(), 800.dp.toPx().toInt())

    val imageRequest = remember {
        ImageRequest.Builder(context)
            .data(category.thumbnail)
            .diskCachePolicy(CachePolicy.ENABLED)
            .size(categoryImageSize)
            .build()
    }


    var showShimmer by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .height(100.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = imageRequest,
            contentDescription = category.name,
            contentScale = ContentScale.Crop,
            onSuccess = {
                showShimmer = false
            },
            modifier = Modifier
                .matchParentSize()
                .background(
                    shimmerBrush(targetValue = 1300f, showShimmer = showShimmer),
                    shape = RoundedCornerShape(10.dp)
                )
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.Black, Color(0xFF29323B)
                        )
                    ),
                    alpha = if (isSystemInDarkTheme()) 0.45f else 0.35f
                ),
            contentAlignment = Alignment.Center
        ) {

            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium
                    .copy(fontWeight = FontWeight.SemiBold, fontSize = 20.sp),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}


@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}