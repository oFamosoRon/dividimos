package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.ofamosoron.dividimos.ui.composables.header.CustomTopBar
import com.ofamosoron.dividimos.ui.navigation.Route
import com.ofamosoron.dividimos.ui.theme.DividimosTheme

@Composable
fun ServiceFeeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Taxa de Serviço",
                showActionMenu = false,
                actionMenuClearOrderClick = { /*TODO*/ },
                actionMenuAddServiceFeeClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier.clickable {
                        navController.navigate(route = Route.HomeScreen.url) {
                            popUpTo(route = Route.AddServiceFeeScreen.url) {
                                inclusive = true
                            }
                        }
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = padding.calculateTopPadding())
        ) {

        }

    }
}

@Preview
@Composable
fun PreviewServiceFeeScreen() {
    DividimosTheme {
        ServiceFeeScreen(navController = NavController(LocalContext.current))
    }
}