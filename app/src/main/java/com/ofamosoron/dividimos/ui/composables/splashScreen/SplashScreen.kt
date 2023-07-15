package com.ofamosoron.dividimos.ui.composables.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.navigation.Route

@Composable
fun SplashScreen(
    navController: NavController,
    viewModel: SplashScreenViewModel = hiltViewModel()
) {
    val state = viewModel.splashScreenState.collectAsState()

    val route = if (state.value.showTutorial) {
        Route.HomeScreen.url
    } else {
        Route.TutorialScreen.url
    }

    if (state.value.isLoaded) {
        viewModel.navigationEnd()
        navController.navigate(route) {
            popUpTo(Route.SplashScreen.url) {
                inclusive = true
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3B2664))
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "splash",
            modifier = Modifier.fillMaxSize()
        )

    }
}
