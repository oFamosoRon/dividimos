package com.ofamosoron.dividimos.ui.homeScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ofamosoron.dividimos.ui.theme.DividimosTheme

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { HomeScreenTopBar() }
    ) { paddingValues ->
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding())
        ) {
            Text(text = "Content")
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    DividimosTheme {
        HomeScreen()
    }
}