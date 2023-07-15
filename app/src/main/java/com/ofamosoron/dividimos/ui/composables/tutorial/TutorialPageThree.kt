package com.ofamosoron.dividimos.ui.composables.tutorial

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ofamosoron.dividimos.ui.navigation.Route

@Composable
fun TutorialPageThree(
    navController: NavController,
    viewModel: TutorialViewModel = hiltViewModel()
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Pronto! Comece a...",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )

            Button(onClick = {
                viewModel.onFinishTutorial()
                navController.navigate(Route.HomeScreen.url) {
                    popUpTo(Route.TutorialScreen.url) {
                        inclusive = true
                    }
                }
            }) {
                Text(text = "Dividir", style = MaterialTheme.typography.headlineMedium)
            }
        }
    }
}
