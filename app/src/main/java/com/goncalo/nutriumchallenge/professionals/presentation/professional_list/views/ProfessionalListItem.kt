package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.professionals.domain.model.Professional

@Composable
fun ProfessionalListItem(
    modifier: Modifier = Modifier,
    professionalInfo: Professional?,
    onCardClicked: (Int) -> Unit
) {
    professionalInfo?.let { professional ->
        Card(modifier = modifier
            .fillMaxWidth()
            .padding(top = 15.dp)
            .clickable { onCardClicked(professional.id) }) {
            Column {
                ProfessionalImageAndInformation(professional = professional)
                ProfessionalExpertiseView(expertiseList = professional.expertise)
            }
        }
    }
}

@Composable
private fun ProfessionalImageAndInformation(
    modifier: Modifier = Modifier,
    professional: Professional
) {
    Row(
        modifier = modifier.padding(15.dp)
    ) {
        AsyncImage(
            model = professional.profilePictureUrl,
            contentDescription = null,
            modifier = Modifier
                .size(75.dp)
                .clip(RoundedCornerShape(12.dp)),
            placeholder = painterResource(id = R.drawable.default_person),
            error = painterResource(id = R.drawable.default_person)
        )

        Column(
            modifier = Modifier.padding(start = 10.dp)
        ) {
            Text(text = professional.name)
            BuildRatingView(
                rating = professional.rating,
                ratingCount = professional.ratingCount
            )
            BuildProfessionalLanguage(languageList = professional.languages)
        }
    }
}

@Composable
fun ProfessionalExpertiseView(modifier: Modifier = Modifier, numItemsToShow: Int = 2, expertiseList: List<String>) {
    Row(
        modifier = modifier
            .padding(horizontal = 15.dp)
            .padding(bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        expertiseList.forEachIndexed { index, expertise ->
            //Show only two items of the list to not overflow the card
            if (index > (numItemsToShow - 1)) {
                return@forEachIndexed
            }

            Card(
                colors = CardDefaults.cardColors().copy(containerColor = Color.White),
                modifier = Modifier.padding(end = 5.dp)
            ) {
                Text(
                    text = expertise,
                    style = TextStyle(textAlign = TextAlign.Center, fontSize = 14.sp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(all = 4.dp)
                )
            }
        }
    }
}