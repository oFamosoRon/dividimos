package com.ofamosoron.dividimos.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun CounterTag(
    color: Color,
    count: Int,
    modifier: Modifier = Modifier,
    textColor: Color,
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(22.dp)
            .background(color)
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "$count", style = TextStyle(color = textColor))
    }
}
