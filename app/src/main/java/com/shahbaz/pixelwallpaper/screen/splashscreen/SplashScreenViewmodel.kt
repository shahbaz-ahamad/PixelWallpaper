package com.shahbaz.pixelwallpaper.screen.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SplashScreenViewmodel : ViewModel() {

    private val _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> = _progress.asStateFlow()

    init {
        updateProgress()
    }

    private fun updateProgress() {
        viewModelScope.launch {
            while (_progress.value < 1f) {
                _progress.value += 0.1f
                delay(100)
            }
        }

    }
}