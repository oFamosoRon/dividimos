package com.ofamosoron.dividimos.ui.composables.tutorial

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ofamosoron.dividimos.R
import com.ofamosoron.dividimos.domain.models.Guest
import com.ofamosoron.dividimos.ui.composables.home.GuestCard

@SuppressWarnings("LongMethod")
@Composable
fun TutorialPageTwo() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        val transitionState = rememberInfiniteTransition()

        val tiltAngle: Float by transitionState.animateFloat(
            initialValue = 0f,
            targetValue = 35f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 2000), // Adjust duration as needed
                repeatMode = RepeatMode.Reverse
            )
        )

        val animatedTilt = animateFloatAsState(targetValue = tiltAngle)

        val alpha by transitionState.animateFloat(
            initialValue = 0f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 4000),
                repeatMode = RepeatMode.Restart
            )
        )

        val animatedAlpha = animateFloatAsState(targetValue = alpha)

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No final a gente vê",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "quanto deu",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "pra cada um!",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineSmall,
            )
        }

        Box(
            modifier = Modifier
                .alpha(animatedAlpha.value)
                .width(250.dp)
                .height(280.dp)
                .clip(RoundedCornerShape(5.dp))
                .background(MaterialTheme.colorScheme.primaryContainer)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(5.dp),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
        ) {
            IconButton(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                //Guest name
                Row(
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(
                        text = "Bia",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                    Spacer(modifier = Modifier.padding(6.dp))
                    Text(
                        text = "R$ 35,00",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.headlineMedium,
                    )
                }
                Spacer(modifier = Modifier.padding(6.dp))
                Divider()
                Spacer(modifier = Modifier.padding(6.dp))
                Spacer(modifier = Modifier.padding(6.dp))
                Text(
                    text = "Fritas",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "R$ 20,00",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(6.dp))

                Text(
                    text = "Suco",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "R$ 15,00",
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.padding(6.dp))

            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
        ) {

            Row(modifier = Modifier) {
                Icon(
                    painterResource(id = R.drawable.ic_pointing_finger),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .size(55.dp)
                        .graphicsLayer {
                            rotationZ = animatedTilt.value
                        }
                )
                GuestCard(guest = Guest(name = "Bia"), onClick = {})
                Spacer(modifier = Modifier.padding(4.dp))
                GuestCard(guest = Guest(name = "Leo"), onClick = {})
            }
        }
    }
}
