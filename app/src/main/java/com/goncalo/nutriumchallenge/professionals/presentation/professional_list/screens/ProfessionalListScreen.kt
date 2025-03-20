package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.goncalo.nutriumchallenge.professionals.presentation.Screens
import com.goncalo.nutriumchallenge.professionals.presentation.common.ShimmerEffect
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalListViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.ProfessionalListHeader
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.ProfessionalListItem

@Composable
fun ProfessionalListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfessionalListViewModel,
    navController: NavController
) {

    val pagingItems = viewModel.professionalsList.collectAsLazyPagingItems()

    if (pagingItems.loadState.refresh is LoadState.Loading) {
        BuildLoadingListEffect()
    } else {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 15.dp)
        ) {

            item {
                ProfessionalListHeader(sortOptionSelected = viewModel.sortOption.collectAsState().value) { option ->
                    viewModel.changeSortOptionSelected(option)
                }
            }

            items(count = pagingItems.itemCount, key = {item -> item.hashCode()}) { index ->
                val professionalItem = pagingItems[index]
                professionalItem?.let { professional ->
                    ProfessionalListItem(professionalInfo = professional) {
                        navController.navigate(Screens.ProfessionalDetails(professional.id))
                    }
                }

            }

            if (pagingItems.loadState.append is LoadState.Loading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
fun BuildLoadingListEffect(modifier: Modifier = Modifier) {
    Column {
        repeat(8) {
            ShimmerEffect(
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }
    }
}


