package com.shahbaz.pixelwallpaper.screen.search

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarDefaults.InputField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.core.provider.FontsContractCompat.Columns
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shahbaz.pixelwallpaper.model.Photo
import com.shahbaz.pixelwallpaper.screen.component.LoadingPlaceHolder
import com.shahbaz.pixelwallpaper.screen.component.WallpaperItem
import com.shahbaz.pixelwallpaper.screen.homescreen.WallpaperViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackArrowPress: () -> Unit
) {

    val wallpaperViewmodel: WallpaperViewmodel = hiltViewModel()


    var searchQuery by rememberSaveable{ mutableStateOf("") }
    var isExpanded by rememberSaveable{ mutableStateOf(false) }


    val localKeyboard = LocalSoftwareKeyboardController.current
    val localFocusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }


    val searchedItem = wallpaperViewmodel.searchWallpaper(searchQuery).collectAsLazyPagingItems()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {

        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(if (!isExpanded) 20.dp else 0.dp),
            windowInsets = if (isExpanded) {
                WindowInsets(0.dp)
            } else {
                WindowInsets(top = 20.dp)
            },
            inputField = {
                InputField(
                    query = searchQuery,
                    onQueryChange = {
                        searchQuery = it
                    },
                    onSearch = {
                        if (searchQuery.isNotEmpty() && searchQuery.length >= 3) {
                            isExpanded = false
                            localKeyboard?.hide()
                            localFocusManager.clearFocus()
                        }
                    },
                    expanded = false,
                    onExpandedChange = {
                        isExpanded = it
                    },
                    placeholder = {
                        Text(text = "Search Here")
                    },
                    leadingIcon = {
                        if (searchQuery.isEmpty() && !isExpanded) Icon(
                            Icons.Default.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        ) else Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                searchQuery = ""
                                isExpanded = false
                                onBackArrowPress()
                            },
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) Icon(
                            Icons.Default.Clear,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                searchQuery = ""
                                isExpanded = true
                            },
                            tint = if (isExpanded) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.7f
                            )
                        )
                    },
                    modifier = if (isExpanded) {
                        Modifier
                            .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                            .focusRequester(focusRequester)
                    } else {
                        Modifier
                            .animateContentSize(spring(stiffness = Spring.StiffnessHigh))
                            .focusRequester(focusRequester)
                    },
                    colors = TextFieldDefaults.colors(
                        focusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            },
            expanded = isExpanded,
            onExpandedChange = {
                isExpanded = it
            },
            colors = SearchBarDefaults.colors(
                containerColor = if (isExpanded) {
                    MaterialTheme.colorScheme.background
                } else {
                    MaterialTheme.colorScheme.surfaceContainerHigh
                },
                dividerColor = MaterialTheme.colorScheme.outline
            )
        ) {

        }

        Spacer(Modifier.height(20.dp))
        showWallpaper(searchedItem)
    }
}

@Composable
fun showWallpaper(searchedItem: LazyPagingItems<Photo>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {

        if (searchedItem.loadState.refresh == LoadState.Loading) {
            items(20) {
                LoadingPlaceHolder()
            }
        }

        items(
            searchedItem.itemCount,
            key = { "${searchedItem[it]?.id}_$it" }
        ) { index ->
            val wallpaper = searchedItem[index]
            if (wallpaper != null) {
                WallpaperItem(imageUrl = wallpaper.src.portrait)
            }
        }
    }

}


