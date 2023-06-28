package com.ofamosoron.dividimos.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun NumberPicker(
    initialValue: Int = 0,
    maxValue: Int = 10,
    minValue: Int = 0,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var value by rememberSaveable { mutableStateOf<Int>(initialValue) }

    LaunchedEffect(initialValue) {
        value = initialValue
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = modifier
    ) {
        TextButton(onClick = {
            if (value > minValue) {
                value--
                onValueChange(value)
            }
        }) {
            NPButton(text = "-")
        }
        Text(
            text = "$value",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )
        TextButton(onClick = {
            if (value < maxValue) {
                value++
                onValueChange(value)
            }
        }) {
            NPButton(text = "+")
        }
    }
}

@Composable
private fun NPButton(text: String) {
    Box(
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .size(60.dp, 40.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.secondary,
                shape = MaterialTheme.shapes.medium
            )
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.surface,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.align(Alignment.Center),
        )
    }
}