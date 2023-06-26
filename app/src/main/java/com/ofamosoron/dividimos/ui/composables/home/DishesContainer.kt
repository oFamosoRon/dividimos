package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.drag_and_drop.DropTarget
import com.ofamosoron.dividimos.ui.theme.*
import com.ofamosoron.dividimos.ui.util.CounterTag
import com.ofamosoron.dividimos.ui.util.EditButton
import com.ofamosoron.dividimos.util.formatMoney
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

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
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalItemSpacing = 4.dp,
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
            Brush.radialGradient(
                center = Offset.Zero,
                radius = 400F,
                colors = listOf(
                    if (isSystemInDarkTheme()) {
                        md_theme_dark_tertiary_gradient
                    } else {
                        md_theme_light_tertiary_gradient
                    },
                    MaterialTheme.colorScheme.tertiary,
                )
            )
        } else {
            Brush.radialGradient(
                center = Offset.Zero,
                radius = 400F,
                colors = listOf(
                    if (isSystemInDarkTheme()) {
                        md_theme_dark_primary_gradient
                    } else {
                        md_theme_light_primary_gradient
                    },
                    MaterialTheme.colorScheme.primary,
                )
            )
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
                .height(180.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(bgColor)
                .clickable { onClick(dish.id) }
        ) {
            Column {
                Text(
                    text = dish.price.cents.formatMoney(),
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = dish.name,
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Total: ${(dish.price.cents * dish.qnt).formatMoney()}",
                    color = MaterialTheme.colorScheme.surface,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 8.dp, bottom = 8.dp)
                .clickable { onEditClick(dish.uuid) }
        ) {
            EditButton()
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 4.dp)
                .clickable { onEditClick(dish.uuid) }
        ) {
            CounterTag(
                color = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.surface,
                count = dish.qnt,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewDishCard() {
    DividimosTheme() {
        DishCard(
            dish = Dish(
                id = 0,
                qnt = 12,
                price = Money(cents = 1000),
                name = "Hambúrguer",
                uuid = "",
                guests = emptyList(),
                createdAt = Clock.System.now(),
                checkId = ""
            ), onClick = {}, onDrop = { guestUuid: String, dishUuid: String ->

            }, onEditClick = {})
    }
}