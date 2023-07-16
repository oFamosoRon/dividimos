package com.ofamosoron.dividimos.ui.composables.header

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.ui.composables.actionMenu.ActionMenu
import com.ofamosoron.dividimos.util.formatMoney

@Composable
fun Header(
    addNewDish: () -> Unit,
    addNewGuest: () -> Unit,
    actionMenuOptionOneClick: () -> Unit,
    total: Long,
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 4.dp, bottom = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.header_title),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = total.formatMoney(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = addNewDish) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_dish),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "add dish"
                    )
                }
                IconButton(onClick = addNewGuest) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add_guest),
                        tint = MaterialTheme.colorScheme.primary,
                        contentDescription = "add guest"
                    )
                }
                ActionMenu(
                    onOptionOneClick = {
                        actionMenuOptionOneClick()
                    }
                )
            }
        }
    }
}
