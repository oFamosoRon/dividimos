package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
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

private const val MAX_ICONS = 5
private const val ZERO: Int = 0
private const val SPACER: Int = 15
private const val ONE: Int = 1
private const val FOUR: Int = 4

@Composable
fun GuestIndicatorContainer(guests: List<String>) {

    var space by rememberSaveable {
        mutableStateOf(ZERO)
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
            guests.subList(ZERO, MAX_ICONS - ONE)
        }
        subList.map { item ->
            GuestIndicator(text = item.substring(ZERO, ONE), space = (space).dp)
            space += SPACER
        }
        if (guests.size >= MAX_ICONS) {
            Text(text = "+${(guests.size - FOUR)}", modifier = Modifier.offset(64.dp))
        }
        space = ZERO
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
