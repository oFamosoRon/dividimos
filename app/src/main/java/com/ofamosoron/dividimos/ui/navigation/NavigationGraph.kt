package com.ofamosoron.dividimos.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ofamosoron.dividimos.ui.composables.home.Home
import com.ofamosoron.dividimos.ui.composables.splash_screen.SplashScreen

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = Route.SplashScreen.url) {
        composable(route = Route.SplashScreen.url) {
            SplashScreen(navController = navController)
        }

        composable(route = Route.HomeScreen.url) {
            Home(navController = navController)
        }
    }
}