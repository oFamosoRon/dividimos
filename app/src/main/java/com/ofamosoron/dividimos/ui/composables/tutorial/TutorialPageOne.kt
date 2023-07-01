package com.ofamosoron.dividimos.ui.composables.tutorial

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.domain.models.Dish
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.domain.models.Money
import com.ofamosoron.dividimos.ui.composables.home.DishCard
import com.ofamosoron.dividimos.ui.composables.home.GuestCard

@Composable
fun TutorialPageOne() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        val transitionState = rememberInfiniteTransition()

        val progress by transitionState.animateFloat(
            initialValue = 1f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000), // Adjust duration as needed
                repeatMode = RepeatMode.Restart
            )
        )

        val animatedOffsetY = animateFloatAsState(targetValue = 1f - progress)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Arraste amigos para",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "dividir um lanche",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "com você!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        Box(modifier = Modifier.width(180.dp)) {
            DishCard(
                guests = emptyList(),
                dish = Dish(name = "Fritas", price = Money(cents = 2000), qnt = 1),
                onIncreaseClick = {},
                onDecreaseClick = {},
                onDrop = { _, _ -> },
                onEditClick = { _ ->}
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
        ) {
            Row() {
                Box(modifier = Modifier.offset {
                    IntOffset(
                        x = 0,
                        y = (animatedOffsetY.value * 400).toInt()
                    )
                }) {
                    GuestCard(guest = Guest(name = "Bia"), onClick = {})
                }
                Spacer(modifier = Modifier.padding(4.dp))
                GuestCard(guest = Guest(name = "Leo"), onClick = {})
            }
        }
    }
}