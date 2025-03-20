package com.goncalo.nutriumchallenge.professionals.domain.model

import androidx.compose.runtime.Immutable
import com.google.gson.annotations.SerializedName

@Immutable
data class Professional(
    val expertise: List<String>,
    val id: Int,
    val languages: List<String>,
    val name: String,
    @SerializedName("profile_picture_url")
    val profilePictureUrl: String,
    val rating: Int,
    @SerializedName("rating_count")
    val ratingCount: Int,
    @SerializedName("about_me")
    val aboutMe: String? = null
)
