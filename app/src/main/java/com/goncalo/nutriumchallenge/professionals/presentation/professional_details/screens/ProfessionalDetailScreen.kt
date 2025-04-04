package com.goncalo.nutriumchallenge.professionals.presentation.professional_details.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional
import com.goncalo.nutriumchallenge.professionals.presentation.common.helpers.UiState
import com.goncalo.nutriumchallenge.professionals.presentation.common.views.FlowRowView
import com.goncalo.nutriumchallenge.professionals.presentation.common.views.ShimmerEffect
import com.goncalo.nutriumchallenge.professionals.presentation.professional_details.viewmodel.ProfessionalDetailViewModel
import com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views.BuildProfessionalLanguage
import com.goncalo.nutriumchallenge.professionals.presentation.common.views.BuildRatingView

@Composable
fun ProfessionalDetailScreen(
    professionalUniqueId: Int,
    navController: NavController,
    viewModel: ProfessionalDetailViewModel,
) {

    LaunchedEffect(Unit) {
        viewModel.getProfessionalDetail(professionalUniqueId)
    }

    when(val state = viewModel.uiState.collectAsState().value) {
        is UiState.Loading -> ProfessionalDetailLoadingScreen()
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
                    ProfessionalDetailInformation(
                        modifier = Modifier.padding(
                            start = 16.dp,
                            top = 16.dp,
                            end = 16.dp
                        ), professional = professional
                    )
                }
            }
        }

        is UiState.Error -> ProfessionalDetailErrorScreen()

        else -> Unit
    }
}

@Composable
private fun ProfessionalDetailLoadingScreen(modifier: Modifier = Modifier) {
    Column {
        ShimmerEffect(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp))
        )

        ShimmerEffect(
            modifier = modifier
                .fillMaxWidth()
                .height(150.dp)
                .padding(16.dp)
                .clip(RoundedCornerShape(12.dp))
        )
    }
}

@Composable
private fun ProfessionalDetailErrorScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.sad_face),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Text(
                text = "An error appeared while loading the details. Try again later.",
                modifier = Modifier.padding(top = 16.dp),
                style = TextStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
            )
        }
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
    Column(modifier = modifier
        .fillMaxWidth()
        .shadow(45.dp)
        .background(
            color = Color.Gray,
            shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)
        )
        .padding(16.dp)) {
        Row(
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

                BuildProfessionalLanguage(
                    modifier = Modifier.padding(top = 10.dp),
                    languageList = professional.languages,
                    numLanguageDisplay = professional.languages.size
                )
            }
        }

        FlowRowView(
            modifier = Modifier.padding(top = 16.dp),
            itemList = professional.expertise
        )
    }


}

@Composable
fun ProfessionalDetailInformation(modifier: Modifier = Modifier, professional: Professional) {
    var seeMore by remember {
        mutableStateOf(false)
    }

    val aboutMeTextSplitNumStrings = remember {
        2
    }

    Column(
        modifier = modifier
    ) {
        professional.aboutMe?.let { aboutMeText ->
            val aboutMeTextSplit = aboutMeText.split(".")
            val t = aboutMeTextSplit.take(aboutMeTextSplitNumStrings)
                .joinToString(separator = ".")

            Text(
                text = stringResource(id = R.string.about_me),
                style = TextStyle(fontWeight = FontWeight.SemiBold, fontSize = 18.sp)
            )
            Text(
                text = if (seeMore) aboutMeText else t,
                modifier = Modifier.animateContentSize()
            )

            if (aboutMeTextSplit.size > aboutMeTextSplitNumStrings) {
                val btnText = if (seeMore) "See Less" else "See More"
                val btnIcon =
                    if (seeMore) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown

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
    }
}
