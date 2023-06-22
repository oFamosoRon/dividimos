package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.ui.drag_and_drop.DropTarget
import com.ofamosoron.dividimos.ui.util.CounterTag
import com.ofamosoron.dividimos.util.formatMoney

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.DishesContainer(
    dishes: List<Dish>,
    onClick: (Int) -> Unit,
    onDrop: (guestUuid: String, dishUuid: String) -> Unit,
    onLongPress: (dishUuid: String) -> Unit
) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp
    ) {
        items(items = dishes) { dish ->
            DishCard(
                dish = dish,
                onDrop = { guestUuid: String, dishUuid: String ->
                    onDrop(guestUuid, dishUuid)
                },
                onClick = { onClick(dish.id) },
                onEditClick = { onLongPress(dish.uuid) }
            )
        }
    }

}

@Composable
fun DishCard(
    dish: Dish,
    onClick: (Int) -> Unit,
    onDrop: (guestUuid: String, dishUuid: String) -> Unit,
    onEditClick: (dishUui: String) -> Unit
) {

    DropTarget<Guest>(modifier = Modifier) { isInBound, guest ->

        val bgColor = if (isInBound) {
            MaterialTheme.colorScheme.tertiary
        } else {
            MaterialTheme.colorScheme.primary
        }

        guest?.let {
            if (isInBound) {
                onDrop(guest.uuid, dish.uuid)
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .clip(MaterialTheme.shapes.medium)
                .background(bgColor)
                .clickable { onClick(dish.id) }
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = dish.name,
                        style = TextStyle(
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.surface
                        )
                    )
                    CounterTag(
                        color = MaterialTheme.colorScheme.secondary,
                        textColor = MaterialTheme.colorScheme.surface,
                        count = dish.qnt,
                    )
                }
                Text(
                    text = "Total: ${(dish.price.cents * dish.qnt).formatMoney()}",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.surface,
                        fontStyle = FontStyle.Italic
                    )
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 4.dp, bottom = 4.dp)
                .clickable { onEditClick(dish.uuid) }
        ) {
            EditButton()
        }
    }
}

@Composable
fun EditButton() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clip(MaterialTheme.shapes.medium)
            .size(width = 50.dp, height = 30.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Icon(
            Icons.Outlined.Edit,
            contentDescription = "edit",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}