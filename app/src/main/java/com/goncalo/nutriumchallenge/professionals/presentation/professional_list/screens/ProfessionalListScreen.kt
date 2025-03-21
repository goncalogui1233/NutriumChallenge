package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.presentation.Screens
import com.goncalo.nutriumchallenge.professionals.presentation.common.views.ShimmerEffect
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.ProfessionalListViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.ProfessionalListHeader
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.ProfessionalListItem

@OptIn(ExperimentalMaterial3Api::class)
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
        PullToRefreshBox(isRefreshing = false, onRefresh = { pagingItems.refresh() }) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 15.dp)
            ) {

                item {
                    ProfessionalListHeader(sortOptionSelected = viewModel.sortOption.collectAsState().value) { option ->
                        viewModel.changeSortOptionSelected(option)
                    }
                }

                if (pagingItems.loadState.refresh is LoadState.NotLoading) {
                    items(count = pagingItems.itemCount,
                        key = { item -> pagingItems[item]?.uniqueId ?: item.hashCode() }) { index ->
                        val professionalItem = pagingItems[index]
                        professionalItem?.let { professional ->
                            ProfessionalListItem(professionalInfo = professional) {
                                navController.navigate(Screens.ProfessionalDetails(professional.uniqueId))
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
                } else {
                    item {
                        ProfessionalListError()
                    }
                }
            }
        }

    }
}

@Composable
private fun ProfessionalListError(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.sad_face),
            contentDescription = null,
            modifier = Modifier.size(32.dp)
        )

        Text(
            text = "An error appeared while loading list. Try again later.",
            modifier = Modifier.padding(top = 16.dp),
            style = TextStyle(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        )
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


