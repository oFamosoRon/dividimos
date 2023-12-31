package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GuestIndicatorContainer(guests: List<String>) {

    val MAX_ICONS = 5

    var space by rememberSaveable {
        mutableStateOf(0)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentWidth()
            .padding(start = 8.dp)
    ) {
        val subList = if (guests.size < MAX_ICONS) {
            guests
        } else {
            guests.subList(0, MAX_ICONS - 1)
        }
        subList.map { item ->
            GuestIndicator(text = item.substring(0, 1), space = (space).dp)
            space += 15
        }
        if (guests.size >= 5) {
            Text(text = "+${(guests.size - 4)}", modifier = Modifier.offset(64.dp))
        }
        space = 0
    }


}

@Composable
fun GuestIndicator(
    text: String,
    space: Dp
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .offset(space)
            .size(18.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.tertiaryContainer)
            .border(
                width = 1.dp,
                shape = CircleShape,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )

    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onTertiaryContainer,
            style = MaterialTheme.typography.bodySmall
        )
    }
}