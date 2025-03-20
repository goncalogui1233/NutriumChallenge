package com.goncalo.nutriumchallenge.professionals.presentation.professional_details.screens

import android.icu.text.CaseMap.Title
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel.ProfessionalDetailViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.viewmodel.UiState
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.BuildRatingView

@Composable
fun ProfessionalDetailScreen(
    professionalId: Int,
    navController: NavController,
    viewModel: ProfessionalDetailViewModel,
) {

    LaunchedEffect(Unit) {
        viewModel.getProf(professionalId)
    }

    when(val state = viewModel.uiState.collectAsState().value) {
        is UiState.Loading -> CircularProgressIndicator()
        is UiState.Success -> {
            val scrollState = rememberScrollState()
            val professional = state.data
            professional?.let {
                ProfessionalDetailTopBar {
                    navController.popBackStack()
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 56.dp)
                        .verticalScroll(state = scrollState)
                ) {
                    ProfessionalDetailHeader(professional = professional)
                    ProfessionalDetailInformations(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        ), professional = professional
                    )
                }
            }
        }

        is UiState.Error -> {
            Text(text = state.message ?: "")
        }

        else -> Unit
    }
}

@Composable
fun ProfessionalDetailTopBar(modifier: Modifier = Modifier, onBackClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp), contentAlignment = Alignment.CenterStart
    ) {
        IconButton(onClick = { onBackClick() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null
            )
        }
    }
}

@Composable
fun ProfessionalDetailHeader(modifier: Modifier = Modifier, professional: Professional) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(45.dp)
            .background(
                color = Color.Gray,
                shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        AsyncImage(
            model = professional.profilePictureUrl,
            contentDescription = null,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp)),
            placeholder = painterResource(id = R.drawable.default_person),
            error = painterResource(id = R.drawable.default_person)
        )

        Column(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Text(
                text = professional.name,
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            )

            BuildRatingView(
                modifier = Modifier.padding(top = 10.dp),
                rating = professional.rating,
                ratingCount = professional.ratingCount
            )
        }
    }
}

@Composable
fun ProfessionalDetailInformations(modifier: Modifier = Modifier, professional: Professional) {
    var seeMore by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
    ) {
        professional.aboutMe?.let {
            Text(
                text = "About me",
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )
            Text(
                text = "${professional.aboutMe}",
                maxLines = (if (seeMore) Int.MAX_VALUE else 2),
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.animateContentSize()
            )
        }

        AnimatedVisibility(seeMore) {
            FlowRowItemView(
                modifier = Modifier.padding(top = 16.dp),
                title = "Areas of Expertise",
                itemList = professional.expertise
            )
        }

        AnimatedVisibility(seeMore) {
            FlowRowItemView(
                modifier = Modifier.padding(top = 16.dp),
                title = "Spoken Languages",
                itemList = professional.languages
            )
        }


        val btnText = if(seeMore) "See Less" else "See More"
        val btnIcon = if(seeMore) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

        val interactionSource = remember { MutableInteractionSource() }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, end = 16.dp)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) { seeMore = !seeMore },
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = btnText, modifier = Modifier.padding(end = 4.dp))
            Icon(imageVector = btnIcon, contentDescription = null)
        }
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowItemView(modifier: Modifier = Modifier, title: String, itemList: List<String>) {
    if (itemList.isNotEmpty()) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = title,
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )

            FlowRow() {
                itemList.forEach { item ->
                    Card(
                        modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                        border = BorderStroke(1.dp, Color.Black)
                    ) {
                        Text(text = item, modifier = Modifier.padding(4.dp))
                    }
                }
            }
        }
    }
}