package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.goncalo.nutriumchallenge.R

@Composable
fun BuildRatingView(modifier: Modifier = Modifier, rating: Int, ratingCount: Int) {
    val maxStars = remember {
        5
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(rating) {
            Image(
                painter = painterResource(id = R.drawable.star),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Yellow
                )
            )
        }

        repeat(maxStars - rating) {
            Image(
                painter = painterResource(id = R.drawable.star_outline),
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    Color.Yellow
                )
            )
        }

        Text(text = "$rating/$maxStars (${ratingCount})", style = TextStyle(fontSize = 14.sp))
    }
}