package com.ofamosoron.dividimos.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.ofamosoron.dividimos.ui.navigation.NavigationGraph
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DividimosTheme {
                val navController = rememberNavController()

                NavigationGraph(navController = navController)
            }
        }
    }
}
