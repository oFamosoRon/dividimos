package com.ofamosoron.dividimos.ui.navigation

sealed class Route(val url: String) {
    object HomeScreen : Route(url = "route_home_screen")
    object SplashScreen : Route(url = "route_splash_screen")
    object TutorialScreen : Route(url = "route_tutorial_screen")
    object NewGuestScreen : Route(url = "route_new_guest_screen")
}
