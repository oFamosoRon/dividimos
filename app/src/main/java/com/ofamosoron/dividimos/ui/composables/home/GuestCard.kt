package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.ui.drag_and_drop.DragTarget

@Composable
fun BoxScope.GuestsContainer(
    guests: List<Guest>,
    cardClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp)
            .align(Alignment.BottomStart)
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
    DragTarget(dataToDrop = guest, modifier = Modifier) {
        Card(shape = MaterialTheme.shapes.medium,
            modifier = Modifier.clickable { onClick(guest.uuid) }) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.secondary)
                    .padding(16.dp)
            ) {
                Text(text = guest.name, color = MaterialTheme.colorScheme.surface)
            }
        }
    }
}