package com.ofamosoron.dividimos.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR_ACTIONS
import com.ofamosoron.dividimos.ui.homeScreen.HomeScreenTags.TOP_BAR_LABEL
import com.ofamosoron.dividimos.ui.theme.DividimosTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {

    val isActionMenuExpanded = rememberSaveable { mutableStateOf<Boolean>(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Dividimos",
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .testTag(TOP_BAR_LABEL)
            )
        },
        actions = {
            IconButton(
                onClick = { isActionMenuExpanded.value = !isActionMenuExpanded.value },
                modifier = Modifier.testTag(TOP_BAR_ACTIONS)
            ) {
                Icon(
                    Icons.Default.MoreVert,
                    contentDescription = "kebab menu",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            DropdownMenu(
                expanded = isActionMenuExpanded.value,
                onDismissRequest = { isActionMenuExpanded.value = false },
            ) {
                DropdownMenuItem(text = { Text(text = "Taxa de serviço") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text(text = "Couvert Artistico") }, onClick = { /*TODO*/ })
                DropdownMenuItem(text = { Text(text = "Fechar a conta") }, onClick = { /*TODO*/ })
            }
        },
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        modifier = Modifier
            .testTag(TOP_BAR)
            .background(MaterialTheme.colorScheme.primary)
    )
}

@Preview
@Composable
fun HomeScreenPreview() {
    DividimosTheme {
        HomeScreen()
    }
}

object HomeScreenTags {
    const val TOP_BAR = "TOP_BAR"
    const val TOP_BAR_LABEL = "TOP_BAR_LABEL"
    const val TOP_BAR_ACTIONS = "TOP_BAR_ACTIONS"
}