package com.ofamosoron.dividimos.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun EditButton() {
    Icon(
        Icons.Outlined.Edit,
        contentDescription = "edit",
        tint = MaterialTheme.colorScheme.surface,
    )
}