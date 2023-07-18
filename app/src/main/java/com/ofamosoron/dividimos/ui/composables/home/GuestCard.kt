package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.ui.dragAndDrop.DragTarget

@Composable
fun BoxScope.GuestsContainer(
    guests: List<Guest>,
    cardClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(end = 4.dp)
    ) {
        items(items = guests) { guest ->
            GuestCard(guest = guest) { guestUuid ->
                cardClick(guestUuid)
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun GuestCard(guest: Guest, onClick: (String) -> Unit) {
    DragTarget(dataToDrop = guest) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(5.dp))
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
                .padding(6.dp)
                .clickable { onClick(guest.uuid) })
        {
            Text(text = guest.name, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}
