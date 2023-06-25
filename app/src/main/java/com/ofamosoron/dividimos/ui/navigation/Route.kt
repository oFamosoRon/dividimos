package com.ofamosoron.dividimos.ui.navigation

sealed class Route(val url: String) {
    object SplashScreen: Route(url = "route_splash_screen")
    object HomeScreen: Route(url =  "route_home_screen")
}
