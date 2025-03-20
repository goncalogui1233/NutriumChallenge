package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalSort

@Composable
fun ProfessionalListHeader(
    modifier: Modifier = Modifier,
    sortOptionSelected: ProfessionalSort,
    onOptionClicked: (ProfessionalSort) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Professionals List",
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Box {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = null
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {

                ProfessionalSort.entries.forEach {
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (sortOptionSelected == it) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 5.dp)
                                    )
                                }
                                Text(text = getSortOptionText(it))
                            }
                        },
                        onClick = { onOptionClicked(it) })
                }
            }
        }


    }

}

private fun getSortOptionText(option: ProfessionalSort):String {
    return when(option) {
        ProfessionalSort.BEST_MATCH -> "Best Match"
        ProfessionalSort.RATING -> "Rating"
        ProfessionalSort.MOST_POPULAR -> "Most Popular"
    }
}