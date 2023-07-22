package com.ofamosoron.dividimos.ui.composables.extraFee

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
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
import com.ofamosoron.dividimos.util.Constants.PERCENT_DIVISOR

@Composable
fun ExtraFee(
    feeType: FeeType,
    serviceValue: Float,
    onClick: () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(100.dp))
            .background(MaterialTheme.colorScheme.tertiary)
            .padding(vertical = 8.dp, horizontal = 16.dp)
            .clickable { onClick() }
    ) {
        val label = when (feeType) {
            FeeType.Couvert -> "Couvert: $serviceValue"
            FeeType.Service -> "Serviço: $serviceValue%"
        }
        Text(
            text = label,
            color = MaterialTheme.colorScheme.onTertiary,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
fun PreviewServiceFee() {
    DividimosTheme {
        ExtraFee(FeeType.Service, PERCENT_DIVISOR.toFloat(), {})
    }
}
