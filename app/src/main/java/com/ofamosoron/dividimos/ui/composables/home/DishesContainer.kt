package com.ofamosoron.dividimos.ui.composables.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.DishToGuests
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.composables.admob.BannerAd
import com.ofamosoron.dividimos.ui.dragAndDrop.DropTarget
import com.ofamosoron.dividimos.ui.theme.DividimosTheme
import com.ofamosoron.dividimos.ui.theme.md_theme_dark_primary_gradient
import com.ofamosoron.dividimos.ui.theme.md_theme_dark_tertiary_gradient
import com.ofamosoron.dividimos.ui.theme.md_theme_light_primary_gradient
import com.ofamosoron.dividimos.ui.theme.md_theme_light_tertiary_gradient
import com.ofamosoron.dividimos.ui.util.CounterTag
import com.ofamosoron.dividimos.ui.util.EditButton
import com.ofamosoron.dividimos.util.formatMoney
import kotlinx.datetime.Clock

@SuppressWarnings("LongParameterList")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.DishesContainer(
    guests: List<DishToGuests>,
    dishes: List<Dish>,
    onIncreaseClick: (String) -> Unit,
    onDecreaseClick: (String) -> Unit,
    onDrop: (guestUuid: String, dishUuid: String) -> Unit,
    onEditClick: (dishUuid: String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier.padding(start = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalItemSpacing = 4.dp,
        ) {
            items(items = dishes) { dish ->
                DishCard(
                    dish = dish,
                    onDrop = { guestUuid: String, dishUuid: String ->
                        onDrop(guestUuid, dishUuid)
                    },
                    onIncreaseClick = { onIncreaseClick(dish.uuid) },
                    onDecreaseClick = { onDecreaseClick(dish.uuid) },
                    onEditClick = { onEditClick(dish.uuid) },
                    guests = guests.find { it.dishUuid == dish.uuid }?.guests ?: emptyList()
                )
            }
        }
    }

}

@SuppressWarnings("LongParameterList", "LongMethod")
@Composable
fun DishCard(
    guests: List<Guest>,
    dish: Dish,
    onIncreaseClick: (String) -> Unit,
    onDecreaseClick: (String) -> Unit,
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
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp, end = 8.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
            ) {
                if (guests.isNotEmpty()) {
                    GuestIndicatorContainer(guests = guests.map { it.name })
                }
            }
            EditButton(
                onClick = { onEditClick(dish.uuid) },
                dishUuid = dish.uuid,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 4.dp, end = 6.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_increase),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onIncreaseClick(dish.uuid) }
                )
                CounterTag(
                    color = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.surface,
                    count = dish.qnt,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_decrease),
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable { onDecreaseClick(dish.uuid) }
                )
            }

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
            ),
            onIncreaseClick = {},
            onDrop = { guestUuid: String, dishUuid: String ->

            },
            onEditClick = {},
            guests = listOf(Guest(name = "Roney"), Guest(name = "Bia"), Guest(name = "Andre")),
            onDecreaseClick = {}
        )
    }
}
