package com.ofamosoron.dividimos.ui.composables.actionMenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ActionMenu(
    onClearOrderClick: () -> Unit,
    onAddServiceFeeClick: () -> Unit,
    onAddCouvertClick: () -> Unit
) {
    val expandedState = remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {
        Box(
            modifier = Modifier
                .clickable { expandedState.value = true }
        ) {
            Icon(
                Icons.Default.MoreVert,
                tint = MaterialTheme.colorScheme.onPrimary,
                contentDescription = "menu",
            )
        }

        DropdownMenu(
            expanded = expandedState.value,
            onDismissRequest = { expandedState.value = false }
        ) {
//            DropdownMenuItem(
//                onClick = { /* TODO */ },
//                text = { Text(text = "Sobre nos") }
//            )


            DropdownMenuItem(
                onClick = {
                    onAddCouvertClick()
                    expandedState.value = false
                },
                text = { Text(text = "Courvert Artistico") }
            )

            DropdownMenuItem(
                onClick = {
                    onAddServiceFeeClick()
                    expandedState.value = false
                },
                text = { Text(text = "Taxa de serviço") }
            )

            DropdownMenuItem(
                onClick = {
                    onClearOrderClick()
                    expandedState.value = false
                },
                text = { Text(text = "Fechar a conta") }
            )
        }
    }
}
