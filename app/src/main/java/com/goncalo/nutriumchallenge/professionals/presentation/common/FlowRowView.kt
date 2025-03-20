package com.goncalo.nutriumchallenge.professionals.presentation.common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowView(modifier: Modifier = Modifier, title: String, itemList: List<String>) {
    if (itemList.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )

            FlowRow(
                modifier = Modifier.padding(top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                itemList.forEach { item ->
                    Card(
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = item, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}