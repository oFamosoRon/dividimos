package com.ofamosoron.dividimos.ui.composables.splash_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {

    private val _splashScreenState = MutableStateFlow(SplashScreenState())
    val splashScreenState = _splashScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            delay(2000)
            _splashScreenState.value = _splashScreenState.value.copy(isLoaded = true)
        }
    }

    fun navigationEnd() {
        _splashScreenState.value = _splashScreenState.value.copy(isLoaded = false)
    }
}