package com.ofamosoron.dividimos.ui.util

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmptyScreen(
    msg: Int,
    icon: Int,
    fontSize: TextUnit = 32.sp,
    space: Dp = 0.dp
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(msg),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.alpha(0.2F)
        )
        Spacer(modifier = Modifier.padding(space))
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "empty",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .alpha(0.2F)
                .size(100.dp)
        )
    }
}
