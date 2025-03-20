package com.goncalo.nutriumchallenge.professionals.presentation.professional_list.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goncalo.nutriumchallenge.R
import com.goncalo.nutriumchallenge.common.getWordListAsString

@Composable
fun BuildProfessionalLanguage(modifier: Modifier = Modifier, languageList: List<String>, numLanguageDisplay: Int = 2) {
    Row(
        modifier = modifier, verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.globe),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = languageList.getWordListAsString(numLanguageDisplay),
            modifier = Modifier.padding(start = 5.dp),
            style = TextStyle(fontSize = 14.sp)
        )
    }
}