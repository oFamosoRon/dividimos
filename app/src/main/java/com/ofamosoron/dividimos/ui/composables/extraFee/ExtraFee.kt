package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.ui.theme.DividimosTheme

@Composable
fun ExtraFee(
    serviceValue: String
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(16.dp)
    ) {
        Text(
            text = "Serviço: $serviceValue",
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.headlineMedium,
        )
    }
}

@Preview
@Composable
fun PreviewServiceFee() {
    DividimosTheme {
        ExtraFee("10%")
    }
}