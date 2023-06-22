package com.ofamosoron.dividimos.ui.composables.header

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ofamosoron.dividimos.ui.composables.home.ActionMenu
import com.ofamosoron.dividimos.util.formatMoney
import com.ofamosoron.dividimos.R

@Composable
fun Header(
    addNewDish: () -> Unit,
    addNewGuest: () -> Unit,
    actionMenuOptionOneClick: () -> Unit,
    actionMenuOptionTwoClick: () -> Unit,
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
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = total.formatMoney(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Italic,
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
                    },
                    onOptionTwoClick = {
                        actionMenuOptionTwoClick()
                    }
                )
            }
        }
    }
}