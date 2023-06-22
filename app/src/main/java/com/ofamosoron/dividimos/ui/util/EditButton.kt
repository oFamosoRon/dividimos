package com.ofamosoron.dividimos.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun EditButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .size(width = 50.dp, height = 30.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Icon(
            Icons.Outlined.Edit,
            contentDescription = "edit",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}