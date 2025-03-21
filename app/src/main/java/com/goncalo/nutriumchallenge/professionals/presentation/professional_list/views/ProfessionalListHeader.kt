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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.ProfessionalSortType
import kotlin.math.exp

@Composable
fun ProfessionalListHeader(
    modifier: Modifier = Modifier,
    sortOptionSelected: ProfessionalSortType?,
    onOptionClicked: (ProfessionalSortType) -> Unit
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
            text = stringResource(id = R.string.professionals_list),
            style = TextStyle(
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        Box {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    modifier = Modifier.size(28.dp),
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = null
                )
            }

            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = !expanded }, shadowElevation = 10.dp) {
                ProfessionalSortType.entries.forEach { entry ->
                    DropdownMenuItem(
                        text = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                if (sortOptionSelected == entry) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = null,
                                        modifier = Modifier.padding(end = 5.dp)
                                    )
                                }
                                Text(text = stringResource(id = getSortOptionText(entry)))
                            }
                        },
                        onClick = { onOptionClicked(entry) })
                }
            }
        }
    }
}

private fun getSortOptionText(option: ProfessionalSortType): Int {
    return when (option) {
        ProfessionalSortType.RATING -> R.string.rating_menu_option
        ProfessionalSortType.MOST_POPULAR -> R.string.most_popular_option
        else -> R.string.best_match_menu_option
    }
}