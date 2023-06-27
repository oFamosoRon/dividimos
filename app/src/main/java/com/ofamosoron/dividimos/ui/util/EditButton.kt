package com.ofamosoron.dividimos.ui.util

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditButton(
    dishUuid: String,
    onClick: (String) -> Unit
) {
    Icon(
        Icons.Outlined.Edit,
        contentDescription = "edit",
        tint = MaterialTheme.colorScheme.surface,
        modifier = Modifier.clickable { onClick(dishUuid) }
    )
}